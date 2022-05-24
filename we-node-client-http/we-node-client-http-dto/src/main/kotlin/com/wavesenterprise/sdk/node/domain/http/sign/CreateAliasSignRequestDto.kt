package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.tx.CreateAliasTxDto
import com.wavesenterprise.sdk.node.domain.sign.CreateAliasSignRequest

data class CreateAliasSignRequestDto(
    override val type: Int = TxType.CREATE_ALIAS.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val feeAssetId: String?,
    val alias: String,
) : SignRequestDto<CreateAliasTxDto> {
    companion object {
        @JvmStatic
        fun CreateAliasSignRequest.toDto(): CreateAliasSignRequestDto =
            CreateAliasSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                alias = alias.value,
            )
    }
}
