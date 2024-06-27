package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.FeeAssetIdMapper.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.OpTypeMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.OpTypeMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.PolicyIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.Util
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.UpdatePolicyTx
import com.wavesenterprise.transaction.protobuf.UpdatePolicyTransaction
import com.wavesenterprise.transaction.protobuf.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.feeAssetIdOrNull
import com.wavesenterprise.transaction.protobuf.updatePolicyTransaction

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
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            policyId = PolicyId(
                txId = TxId(tx.policyId.byteArray()),
            ),
            recipients = tx.recipientsList.map { Address(it.byteArray()) },
            owners = tx.ownersList.map { Address(it.byteArray()) },
            opType = tx.opType.domain(),
            timestamp = Timestamp(tx.timestamp),
            fee = Fee(tx.fee),
            feeAssetId = tx.feeAssetIdOrNull?.let { FeeAssetId.fromByteArray(it.byteArray()) },
            atomicBadge = tx.atomicBadgeOrNull?.domain(),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
