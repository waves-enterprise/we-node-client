package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.ContractApiVersionMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.ContractApiVersionMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.DataEntryMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.DataEntryMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.FeeAssetIdMapper.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesenterprise.sdk.node.client.grpc.mapper.ValidationPolicyMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.ValidationPolicyMapper.dto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.contract.ContractName
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.transaction.protobuf.docker.CreateContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.apiVersionOrNull
import com.wavesenterprise.transaction.protobuf.docker.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.docker.createContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.feeAssetIdOrNull
import com.wavesenterprise.transaction.protobuf.docker.validationPolicyOrNull

object CreateContractTxMapper {
    @JvmStatic
    fun CreateContractTx.dto(): CreateContractTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: CreateContractTx): CreateContractTransaction =
        createContractTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            image = tx.image.value
            imageHash = tx.imageHash.value
            contractName = tx.contractName.value
            params.addAll(tx.params.map { it.dto() })
            fee = tx.fee.value
            timestamp = tx.timestamp.utcTimestampMillis
            setIfNotNull(this::feeAssetId, tx.feeAssetId) { it.bytesValue() }
            setIfNotNull(this::atomicBadge, tx.atomicBadge) { it.dto() }
            setIfNotNull(this::validationPolicy, tx.validationPolicy) { it.dto() }
            setIfNotNull(this::apiVersion, tx.apiVersion) { it.dto() }
            tx.proofs?.also { domainProofs: List<Signature> ->
                proofs += domainProofs.map { it.byteString() }
            }
            senderAddress = tx.senderAddress.byteString()
        }

    @JvmStatic
    fun CreateContractTransaction.domain(version: TxVersion): CreateContractTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: CreateContractTransaction, version: TxVersion): CreateContractTx =
        CreateContractTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            image = ContractImage(tx.image),
            imageHash = ContractImageHash(tx.imageHash),
            contractName = ContractName(tx.contractName),
            params = tx.paramsList.map { it.domain() },
            fee = Fee(tx.fee),
            timestamp = Timestamp(tx.timestamp),
            feeAssetId = tx.feeAssetIdOrNull?.let {
                FeeAssetId(
                    txId = TxId(it.value.byteArray())
                )
            },
            atomicBadge = tx.atomicBadgeOrNull?.domain(),
            validationPolicy = tx.validationPolicyOrNull?.domain(),
            apiVersion = tx.apiVersionOrNull?.domain(),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
