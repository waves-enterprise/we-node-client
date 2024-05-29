package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Decimals
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.IssueTxDescription
import com.wavesenterprise.sdk.node.domain.IssueTxName
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Reissuable
import com.wavesenterprise.sdk.node.domain.Script
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.IssueTx
import com.wavesenterprise.transaction.protobuf.assets.IssueTransaction
import com.wavesenterprise.transaction.protobuf.assets.scriptOrNull

object IssueTxMapper {
    fun dtoInternal(tx: IssueTx): IssueTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun IssueTransaction.domain(version: TxVersion): IssueTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: IssueTransaction, version: TxVersion): IssueTx =
        IssueTx(
            id = TxId(tx.id.byteArray()),
            chainId = ChainId(tx.chainId.toByte()),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            name = IssueTxName(tx.name.byteArray()),
            description = IssueTxDescription(tx.description.byteArray()),
            quantity = Quantity(tx.quantity),
            decimals = Decimals(tx.decimals.toByte()),
            reissuable = Reissuable(tx.reissuable),
            fee = Fee(tx.fee),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            script = tx.scriptOrNull?.let { Script(it.value.byteArray()) },
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
