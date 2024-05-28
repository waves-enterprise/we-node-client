package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.GenesisTx
import com.wavesenterprise.transaction.protobuf.GenesisTransactionOuterClass.GenesisTransaction

object GenesisTxMapper {

    fun dtoInternal(tx: GenesisTx): GenesisTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun GenesisTransaction.domain(version: TxVersion): GenesisTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: GenesisTransaction, version: TxVersion): GenesisTx =
        GenesisTx(
            id = TxId(tx.id.byteArray()),
            amount = Amount(tx.amount),
            fee = Fee(tx.fee),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            version = version,
            recipient = Address(tx.recipient.byteArray()),
            signature = Signature(tx.signature.byteArray()),
        )
}
