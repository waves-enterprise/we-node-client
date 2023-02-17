package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.ContractIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.FeeAssetIdMapper.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx
import com.wavesenterprise.transaction.protobuf.docker.UpdateContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.docker.feeAssetIdOrNull
import com.wavesenterprise.transaction.protobuf.docker.updateContractTransaction

object UpdateContractTxMapper {
    @JvmStatic
    fun UpdateContractTx.dto(): UpdateContractTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: UpdateContractTx): UpdateContractTransaction =
        updateContractTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            contractId = tx.contractId.byteString()
            image = tx.image.value
            imageHash = tx.imageHash.value
            fee = tx.fee.value
            timestamp = tx.timestamp.utcTimestampMillis
            setIfNotNull(this::feeAssetId, tx.feeAssetId) { it.bytesValue() }
            setIfNotNull(this::atomicBadge, tx.atomicBadge) { it.dto() }
            tx.proofs?.also { domainProofs: List<Signature> ->
                proofs += domainProofs.map { it.byteString() }
            }
            senderAddress = tx.senderAddress.byteString()
        }

    @JvmStatic
    fun UpdateContractTransaction.domain(version: TxVersion): UpdateContractTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: UpdateContractTransaction, version: TxVersion): UpdateContractTx =
        UpdateContractTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            contractId = ContractId.fromByteArray(tx.contractId.toByteArray()),
            image = ContractImage(tx.image),
            imageHash = ContractImageHash(tx.imageHash),
            fee = Fee(tx.fee),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            feeAssetId = tx.feeAssetIdOrNull?.let {
                FeeAssetId.fromByteArray(it.byteArray())
            },
            atomicBadge = tx.atomicBadgeOrNull?.domain(),
            proofs = tx.proofsList?.let { dtoProofs ->
                dtoProofs.map {
                    Signature(it.byteArray())
                }
            },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
