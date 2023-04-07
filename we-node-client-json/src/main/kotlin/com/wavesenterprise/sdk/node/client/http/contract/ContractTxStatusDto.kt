package com.wavesenterprise.sdk.node.client.http.contract

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.contract.ContractTxStatus
import com.wavesenterprise.sdk.node.domain.contract.TxStatus

data class ContractTxStatusDto(
    val sender: String,
    val senderPublicKey: String,
    val txId: String,
    val code: Int? = null,
    val message: String,
    val timestamp: Long,
    val signature: String,
    val status: TxStatusDto,
) {

    companion object {
        @JvmStatic
        fun ContractTxStatusDto.toDomain() =
            ContractTxStatus(
                senderAddress = Address.fromBase58(sender),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                txId = TxId.fromBase58(txId),
                code = code,
                message = message,
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                signature = Signature.fromBase58(signature),
                status = TxStatus.valueOf(status.name),
            )
    }
}
