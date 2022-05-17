package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.PolicyDataHashTransaction
import com.wavesenterprise.transaction.protobuf.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.feeAssetIdOrNull
import com.wavesenterprise.transaction.protobuf.policyDataHashTransaction
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AtomicBadgeMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.FeeAssetIdMapper.bytesValue
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesplatform.we.sdk.node.client.grpc.mapper.HashMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PolicyIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.Util
import com.wavesplatform.we.sdk.node.client.tx.PolicyDataHashTx

object PolicyDataHashTxMapper {
    @JvmStatic
    fun PolicyDataHashTx.dto(): PolicyDataHashTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: PolicyDataHashTx): PolicyDataHashTransaction =
        policyDataHashTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            dataHash = tx.dataHash.byteString()
            policyId = tx.policyId.byteString()
            timestamp = tx.timestamp.utcTimestampMillis
            fee = tx.fee.value
            Util.setIfNotNull(this::feeAssetId, tx.feeAssetId) { it.bytesValue() }
            Util.setIfNotNull(this::atomicBadge, tx.atomicBadge) { it.dto() }
            tx.proofs?.also { domainProofs: List<Signature> ->
                proofs += domainProofs.map { it.byteString() }
            }
            senderAddress = tx.senderAddress.byteString()
        }

    @JvmStatic
    fun PolicyDataHashTransaction.domain(version: TxVersion): PolicyDataHashTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: PolicyDataHashTransaction, version: TxVersion): PolicyDataHashTx =
        PolicyDataHashTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            dataHash = Hash(tx.dataHash.byteArray()),
            policyId = PolicyId.fromByteArray(tx.policyId.byteArray()),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            fee = Fee(tx.fee),
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
