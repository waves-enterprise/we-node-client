package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.OpTypeMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.NodeName
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.RegisterNodeTx
import com.wavesenterprise.transaction.protobuf.RegisterNodeTransaction
import com.wavesenterprise.transaction.protobuf.nodeNameOrNull

object RegisterNodeTxMapper {

    fun dtoInternal(tx: RegisterNodeTx): RegisterNodeTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun RegisterNodeTransaction.domain(version: TxVersion): RegisterNodeTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: RegisterNodeTransaction, version: TxVersion): RegisterNodeTx =
        RegisterNodeTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            targetPublicKey = PublicKey(tx.target.byteArray()),
            nodeName = tx.nodeNameOrNull?.let { NodeName(it.value) },
            opType = tx.opType.domain(),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            fee = Fee(tx.fee),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
