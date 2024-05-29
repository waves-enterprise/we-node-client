package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Enabled
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.SponsorFeeTx
import com.wavesenterprise.transaction.protobuf.assets.SponsorFeeTransaction

object SponsorFeeTxMapper {

    fun dtoInternal(tx: SponsorFeeTx): SponsorFeeTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun SponsorFeeTransaction.domain(version: TxVersion): SponsorFeeTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: SponsorFeeTransaction, version: TxVersion): SponsorFeeTx =
        SponsorFeeTx(
            id = TxId(tx.id.byteArray()),
            assetId = AssetId(tx.assetId.byteArray()),
            enabled = Enabled(tx.isEnabled),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            fee = Fee(tx.fee),
            timestamp = Timestamp(tx.timestamp),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
