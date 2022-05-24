package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.tx.AtomicSignInnerTxDto
import com.wavesenterprise.sdk.node.domain.http.tx.AtomicSignInnerTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.tx.AtomicTxDto
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest

data class AtomicSignRequestDto(
    override val type: Int = TxType.ATOMIC.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val txs: List<AtomicSignInnerTxDto>
) : SignRequestDto<AtomicTxDto> {
    companion object {
        @JvmStatic
        fun AtomicSignRequest.toDto(): AtomicSignRequestDto =
            AtomicSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                txs = txs.map { it.toDto() },
            )
    }
}
