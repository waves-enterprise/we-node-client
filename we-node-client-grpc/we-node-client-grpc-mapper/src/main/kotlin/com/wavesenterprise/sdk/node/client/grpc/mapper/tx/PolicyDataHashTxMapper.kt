package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.FeeAssetIdMapper.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.HashMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.PolicyIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.Util
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.transaction.protobuf.PolicyDataHashTransaction
import com.wavesenterprise.transaction.protobuf.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.feeAssetIdOrNull
import com.wavesenterprise.transaction.protobuf.policyDataHashTransaction

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
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            dataHash = Hash(tx.dataHash.byteArray()),
            policyId = PolicyId(
                txId = TxId(tx.policyId.byteArray()),
            ),
            timestamp = Timestamp(tx.timestamp),
            fee = Fee(tx.fee),
            feeAssetId = tx.feeAssetIdOrNull?.let {
                FeeAssetId(
                    txId = TxId(it.value.byteArray()),
                )
            },
            atomicBadge = tx.atomicBadgeOrNull?.domain(),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
