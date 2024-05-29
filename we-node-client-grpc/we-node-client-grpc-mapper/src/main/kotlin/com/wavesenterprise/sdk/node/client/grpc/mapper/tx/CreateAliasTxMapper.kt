package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Alias
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.CreateAliasTx
import com.wavesenterprise.transaction.protobuf.CreateAliasTransaction
import com.wavesenterprise.transaction.protobuf.feeAssetIdOrNull

object CreateAliasTxMapper {

    fun dtoInternal(tx: CreateAliasTx): CreateAliasTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun CreateAliasTransaction.domain(version: TxVersion): CreateAliasTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: CreateAliasTransaction, version: TxVersion): CreateAliasTx =
        CreateAliasTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            alias = Alias(tx.alias.toString()),
            fee = Fee(tx.fee),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            feeAssetId = tx.feeAssetIdOrNull?.let { FeeAssetId.fromByteArray(it.value.toByteArray()) },
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.toByteArray()),
            version = version,
        )
}
