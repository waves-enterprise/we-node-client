package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.tx.SetScriptTxDto
import com.wavesenterprise.sdk.node.domain.sign.SetScriptSignRequest

data class SetScriptSignRequestDto(
    override val type: Int = TxType.SET_SCRIPT.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val name: String,
    val script: String?,
) : SignRequestDto<SetScriptTxDto> {
    companion object {
        @JvmStatic
        fun SetScriptSignRequest.toDto(): SetScriptSignRequestDto =
            SetScriptSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                name = name.asBase58String(),
                script = script?.asBase64String(),
            )
    }
}
