package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.tx.SetAssetScriptTxDto
import com.wavesenterprise.sdk.node.domain.sign.SetAssetScriptSignRequest

data class SetAssetScriptSignRequestDto(
    override val type: Int = TxType.SET_ASSET_SCRIPT.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val script: String?,
    val assetId: String?,
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
