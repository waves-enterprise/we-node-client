package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Script
import com.wavesenterprise.sdk.node.domain.ScriptDescription
import com.wavesenterprise.sdk.node.domain.ScriptName
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.SetScriptTx
import com.wavesenterprise.transaction.protobuf.assets.scriptOrNull
import com.wavesenterprise.transaction.protobuf.smart.SetScriptTransaction
import com.wavesenterprise.transaction.protobuf.smart.scriptOrNull

object SetScriptTxMapper {

    fun dtoInternal(tx: SetScriptTx): SetScriptTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun SetScriptTransaction.domain(version: TxVersion): SetScriptTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: SetScriptTransaction, version: TxVersion): SetScriptTx =
        SetScriptTx(
            id = TxId(tx.id.byteArray()),
            chainId = ChainId(tx.chainId.toByte()),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            script = tx.scriptOrNull?.let { Script(it.value.byteArray()) },
            name = ScriptName(tx.name.byteArray()),
            description = ScriptDescription(tx.description.byteArray()),
            fee = Fee(tx.fee),
            timestamp = Timestamp(tx.timestamp),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
