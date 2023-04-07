package com.wavesenterprise.sdk.node.domain.contract

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId

data class ContractTxStatus(
    val senderAddress: Address,
    val senderPublicKey: PublicKey,
    val txId: TxId,
    val code: Int? = null,
    val message: String,
    val timestamp: Timestamp,
    val signature: Signature,
    val status: TxStatus,
) {

    companion object {
        const val FATAL_ERROR_CODE = 0
        const val RECOVERABLE_ERROR_CODE = 1
    }
}
