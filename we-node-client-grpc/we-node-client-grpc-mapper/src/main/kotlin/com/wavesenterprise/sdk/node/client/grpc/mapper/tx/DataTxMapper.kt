package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.DataEntryMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.DataTx
import com.wavesenterprise.transaction.protobuf.DataTransaction
import com.wavesenterprise.transaction.protobuf.feeAssetIdOrNull

object DataTxMapper {

    fun dtoInternal(tx: DataTx): DataTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun DataTransaction.domain(version: TxVersion): DataTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: DataTransaction, version: TxVersion): DataTx =
        DataTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            authorPublicKey = PublicKey(tx.authorPublicKey.byteArray()),
            data = tx.dataList.map { it.domain() },
            fee = Fee(tx.fee),
            feeAssetId = tx.feeAssetIdOrNull?.let {
                FeeAssetId(
                    txId = TxId(it.value.byteArray()),
                )
            },
            timestamp = Timestamp(tx.timestamp),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            authorAddress = Address(tx.authorPublicKey.byteArray()),
            version = version,
        )
}
