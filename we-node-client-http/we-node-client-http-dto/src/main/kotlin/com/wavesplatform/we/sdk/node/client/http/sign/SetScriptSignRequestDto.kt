package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.SetScriptTxDto
import com.wavesplatform.we.sdk.node.client.sign.SetScriptSignRequest

data class SetScriptSignRequestDto(
    override val type: Int = TxType.SET_SCRIPT.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val name: String,
    val script: String? = null,
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
