package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.docker.DisableContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.docker.disableContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.feeAssetIdOrNull
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.contract.ContractId
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AtomicBadgeMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.ContractIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.FeeAssetIdMapper.bytesValue
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesplatform.we.sdk.node.client.tx.DisableContractTx

object DisableContractTxMapper {
    @JvmStatic
    fun DisableContractTx.dto(): DisableContractTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: DisableContractTx): DisableContractTransaction =
        disableContractTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            contractId = tx.contractId.byteString()
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
    fun DisableContractTransaction.domain(version: TxVersion): DisableContractTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: DisableContractTransaction, version: TxVersion): DisableContractTx =
        DisableContractTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            contractId = ContractId.fromByteArray(tx.contractId.toByteArray()),
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
