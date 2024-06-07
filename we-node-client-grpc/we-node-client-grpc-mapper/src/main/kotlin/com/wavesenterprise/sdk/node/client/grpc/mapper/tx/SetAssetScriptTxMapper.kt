package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Script
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.SetAssetScriptTx
import com.wavesenterprise.transaction.protobuf.assets.SetAssetScriptTransaction
import com.wavesenterprise.transaction.protobuf.assets.scriptOrNull

object SetAssetScriptTxMapper {

    fun dtoInternal(tx: SetAssetScriptTx): SetAssetScriptTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun SetAssetScriptTransaction.domain(version: TxVersion): SetAssetScriptTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: SetAssetScriptTransaction, version: TxVersion): SetAssetScriptTx =
        SetAssetScriptTx(
            id = TxId(tx.id.byteArray()),
            chainId = ChainId(tx.chainId.toByte()),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            assetId = AssetId(tx.assetId.byteArray()),
            script = tx.scriptOrNull?.let { Script(it.value.byteArray()) },
            fee = Fee(tx.fee),
            timestamp = Timestamp(tx.timestamp),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
