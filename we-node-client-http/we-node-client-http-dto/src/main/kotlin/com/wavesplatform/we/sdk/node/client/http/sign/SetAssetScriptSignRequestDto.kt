package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.SetAssetScriptTxDto
import com.wavesplatform.we.sdk.node.client.sign.SetAssetScriptSignRequest

data class SetAssetScriptSignRequestDto(
    override val type: Int = TxType.SET_ASSET_SCRIPT.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val script: String? = null,
    val assetId: String? = null,
) : SignRequestDto<SetAssetScriptTxDto> {
    companion object {
        @JvmStatic
        fun SetAssetScriptSignRequest.toDto(): SetAssetScriptSignRequestDto =
            SetAssetScriptSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                script = script?.asBase64String(),
                assetId = assetId?.asBase58String(),
            )
    }
}
