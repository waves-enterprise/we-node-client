package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.TransferMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Attachment
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.MassTransferTx
import com.wavesenterprise.transaction.protobuf.transfer.MassTransferTransaction
import com.wavesenterprise.transaction.protobuf.transfer.assetIdOrNull
import com.wavesenterprise.transaction.protobuf.transfer.feeAssetIdOrNull

object MassTransferTxMapper {

    fun dtoInternal(tx: MassTransferTx): MassTransferTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun MassTransferTransaction.domain(version: TxVersion): MassTransferTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: MassTransferTransaction, version: TxVersion): MassTransferTx =
        MassTransferTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            assetId = tx.assetIdOrNull?.let { AssetId(it.value.byteArray()) },
            transfers = tx.transfersList.map { it.domain() },
            feeAssetId = tx.feeAssetIdOrNull?.let {
                FeeAssetId(
                    txId = TxId(it.value.byteArray()),
                )
            },
            timestamp = Timestamp(tx.timestamp),
            fee = Fee(tx.fee),
            attachment = Attachment(tx.attachment.byteArray()),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
