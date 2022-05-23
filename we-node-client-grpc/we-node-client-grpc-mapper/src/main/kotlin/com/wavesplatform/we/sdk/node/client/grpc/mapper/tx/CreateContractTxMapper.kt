package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.DataEntry
import com.wavesenterprise.transaction.protobuf.docker.CreateContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.apiVersionOrNull
import com.wavesenterprise.transaction.protobuf.docker.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.docker.createContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.feeAssetIdOrNull
import com.wavesenterprise.transaction.protobuf.docker.validationPolicyOrNull
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.contract.ContractImage
import com.wavesplatform.we.sdk.node.client.contract.ContractName
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AtomicBadgeMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.ContractApiVersionMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.ContractApiVersionMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.DataEntryMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.DataEntryMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.FeeAssetIdMapper.bytesValue
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesplatform.we.sdk.node.client.grpc.mapper.ValidationPolicyMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.ValidationPolicyMapper.dto
import com.wavesplatform.we.sdk.node.client.tx.CreateContractTx

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
            imageHash = tx.imageHash.asHexString()
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
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            image = ContractImage(tx.image),
            imageHash = Hash.fromHexString(tx.imageHash),
            contractName = ContractName(tx.contractName),
            params = tx.paramsList.map { param: DataEntry ->
                param.domain()
            },
            fee = Fee(tx.fee),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            feeAssetId = tx.feeAssetIdOrNull?.let {
                FeeAssetId.fromByteArray(it.byteArray())
            },
            atomicBadge = tx.atomicBadgeOrNull?.domain(),
            validationPolicy = tx.validationPolicyOrNull?.domain(),
            apiVersion = tx.apiVersionOrNull?.domain(),
            proofs = tx.proofsList?.let { dtoProofs ->
                dtoProofs.map {
                    Signature(it.byteArray())
                }
            },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
