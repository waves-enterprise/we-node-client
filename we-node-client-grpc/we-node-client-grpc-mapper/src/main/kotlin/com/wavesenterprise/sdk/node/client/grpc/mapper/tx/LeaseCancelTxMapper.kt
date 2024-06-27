package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.LeaseId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.LeaseCancelTx
import com.wavesenterprise.transaction.protobuf.lease.LeaseCancelTransaction

object LeaseCancelTxMapper {

    fun dtoInternal(tx: LeaseCancelTx): LeaseCancelTransaction {
        TODO("Not yet implemented")
    }

    @JvmStatic
    fun LeaseCancelTransaction.domain(version: TxVersion): LeaseCancelTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: LeaseCancelTransaction, version: TxVersion): LeaseCancelTx =
        LeaseCancelTx(
            id = TxId(tx.id.byteArray()),
            chainId = ChainId(tx.chainId.toByte()),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            fee = Fee(tx.fee),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            leaseId = LeaseId(
                txId = TxId(tx.leaseId.byteArray()),
            ),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
