package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Reissuable
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.ReissueTx
import com.wavesenterprise.transaction.protobuf.assets.ReissueTransaction

object ReissueTxMapper {

    fun dtoInternal(tx: ReissueTx): ReissueTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun ReissueTransaction.domain(version: TxVersion): ReissueTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: ReissueTransaction, version: TxVersion): ReissueTx =
        ReissueTx(
            id = TxId(tx.id.byteArray()),
            chainId = ChainId(tx.chainId.toByte()),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            assetId = AssetId(tx.assetId.byteArray()),
            quantity = Quantity(tx.quantity),
            reissuable = Reissuable(tx.reissuable),
            fee = Fee(tx.fee),
            timestamp = Timestamp(tx.timestamp),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
