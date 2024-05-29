package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.LeaseTx
import com.wavesenterprise.transaction.protobuf.lease.LeaseTransaction
import com.wavesenterprise.transaction.protobuf.lease.assetIdOrNull

object LeaseTxMapper {
    fun dtoInternal(tx: LeaseTx): LeaseTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun LeaseTransaction.domain(version: TxVersion): LeaseTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: LeaseTransaction, version: TxVersion): LeaseTx =
        LeaseTx(
            id = TxId(tx.id.byteArray()),
            assetId = tx.assetIdOrNull?.let { AssetId(it.value.byteArray()) },
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            recipient = Address(tx.recipient.byteArray()),
            amount = Amount(tx.amount),
            fee = Fee(tx.fee),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.toByteArray()),
            version = version,
        )
}
