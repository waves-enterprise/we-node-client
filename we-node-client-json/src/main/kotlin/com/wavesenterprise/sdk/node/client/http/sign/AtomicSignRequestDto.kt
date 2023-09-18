package com.wavesenterprise.sdk.node.client.http.sign

import com.wavesenterprise.sdk.node.client.http.tx.AtomicSignInnerTxDto
import com.wavesenterprise.sdk.node.client.http.tx.AtomicSignInnerTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.AtomicTxDto
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest

data class AtomicSignRequestDto(
    override val type: Int = TxType.ATOMIC.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val transactions: List<AtomicSignInnerTxDto>
) : SignRequestDto<AtomicTxDto> {
    companion object {
        @JvmStatic
        fun AtomicSignRequest.toDto(): AtomicSignRequestDto =
            AtomicSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                transactions = txs.map { it.toDto() },
            )
    }
}
