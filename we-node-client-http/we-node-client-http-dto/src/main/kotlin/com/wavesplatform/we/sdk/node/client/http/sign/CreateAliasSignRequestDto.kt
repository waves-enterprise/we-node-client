package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.CreateAliasTxDto
import com.wavesplatform.we.sdk.node.client.sign.CreateAliasSignRequest

data class CreateAliasSignRequestDto(
    override val type: Int = TxType.CREATE_ALIAS.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val feeAssetId: String? = null,
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
