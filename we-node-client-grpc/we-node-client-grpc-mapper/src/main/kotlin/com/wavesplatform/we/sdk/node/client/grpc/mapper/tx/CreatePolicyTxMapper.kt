package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.CreatePolicyTransaction
import com.wavesenterprise.transaction.protobuf.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.createPolicyTransaction
import com.wavesenterprise.transaction.protobuf.feeAssetIdOrNull
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.PolicyDescription
import com.wavesplatform.we.sdk.node.client.PolicyName
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
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.Util
import com.wavesplatform.we.sdk.node.client.tx.CreatePolicyTx

object CreatePolicyTxMapper {
    @JvmStatic
    fun CreatePolicyTx.dto(): CreatePolicyTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: CreatePolicyTx): CreatePolicyTransaction =
        createPolicyTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            policyName = tx.policyName.value
            description = tx.description.value
            recipients += tx.recipients.map { it.byteString() }
            owners += tx.owners.map { it.byteString() }
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
    fun CreatePolicyTransaction.domain(version: TxVersion): CreatePolicyTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: CreatePolicyTransaction, version: TxVersion): CreatePolicyTx =
        CreatePolicyTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            policyName = PolicyName(tx.policyName),
            description = PolicyDescription(tx.description),
            recipients = tx.recipientsList.map { Address(it.byteArray()) },
            owners = tx.ownersList.map { Address(it.byteArray()) },
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
