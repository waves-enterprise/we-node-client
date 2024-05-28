package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.GenesisRegisterNodeTx
import com.wavesenterprise.transaction.protobuf.GenesisRegisterNodeTransactionOuterClass.GenesisRegisterNodeTransaction

object GenesisRegisterNodeTxMapper {

    fun dtoInternal(tx: GenesisRegisterNodeTx): GenesisRegisterNodeTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun GenesisRegisterNodeTransaction.domain(version: TxVersion): GenesisRegisterNodeTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: GenesisRegisterNodeTransaction, version: TxVersion): GenesisRegisterNodeTx =
        GenesisRegisterNodeTx(
            id = TxId(tx.id.byteArray()),
            targetPublicKey = PublicKey(tx.targetPublicKey.byteArray()),
            fee = Fee(tx.fee),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            version = version,
            signature = Signature(tx.signature.byteArray()),
        )
}
