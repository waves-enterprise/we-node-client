package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.UpdatePolicyTransaction
import com.wavesenterprise.transaction.protobuf.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.feeAssetIdOrNull
import com.wavesenterprise.transaction.protobuf.updatePolicyTransaction
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
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
import com.wavesplatform.we.sdk.node.client.grpc.mapper.OpTypeMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.OpTypeMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PolicyIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.Util
import com.wavesplatform.we.sdk.node.client.tx.UpdatePolicyTx

object UpdatePolicyTxMapper {
    @JvmStatic
    fun UpdatePolicyTx.dto(): UpdatePolicyTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: UpdatePolicyTx): UpdatePolicyTransaction =
        updatePolicyTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            policyId = tx.policyId.byteString()
            recipients += tx.recipients.map { it.byteString() }
            owners += tx.owners.map { it.byteString() }
            opType = tx.opType.dto()
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
    fun UpdatePolicyTransaction.domain(version: TxVersion): UpdatePolicyTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: UpdatePolicyTransaction, version: TxVersion): UpdatePolicyTx =
        UpdatePolicyTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            policyId = PolicyId.fromByteArray(tx.policyId.byteArray()),
            recipients = tx.recipientsList.map { Address(it.byteArray()) },
            owners = tx.ownersList.map { Address(it.byteArray()) },
            opType = tx.opType.domain(),
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
