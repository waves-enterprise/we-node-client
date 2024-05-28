package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.RoleMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.GenesisPermitTx
import com.wavesenterprise.transaction.protobuf.GenesisPermitTransactionOuterClass.GenesisPermitTransaction

object GenesisPermitTxMapper {

    fun dtoInternal(tx: GenesisPermitTx): GenesisPermitTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun GenesisPermitTransaction.domain(version: TxVersion): GenesisPermitTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: GenesisPermitTransaction, version: TxVersion): GenesisPermitTx =
        GenesisPermitTx(
            id = TxId(tx.id.byteArray()),
            target = Address(tx.target.byteArray()),
            role = tx.role.domain(),
            fee = Fee(tx.fee),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            version = version,
            signature = Signature(tx.signature.byteArray()),
        )
}
