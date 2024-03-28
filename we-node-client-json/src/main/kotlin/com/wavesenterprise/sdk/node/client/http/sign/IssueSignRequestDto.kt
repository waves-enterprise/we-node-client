package com.wavesenterprise.sdk.node.client.http.sign

import com.wavesenterprise.sdk.node.client.http.tx.IssueTxDto
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.sign.IssueSignRequest

data class IssueSignRequestDto(
    override val type: Int = TxType.ISSUE.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val name: String,
    val quantity: Long,
    val description: String,
    val decimals: Byte,
    val reissuable: Boolean,
) : SignRequestDto<IssueTxDto> {
    companion object {
        @JvmStatic
        fun IssueSignRequest.toDto(): IssueSignRequestDto =
            IssueSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                name = name.asBase58String(),
                quantity = quantity.value,
                description = description.asBase58String(),
                decimals = decimals.value,
                reissuable = reissuable.value,
            )
    }
}
