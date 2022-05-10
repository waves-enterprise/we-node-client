package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.IssueTxDto
import com.wavesplatform.we.sdk.node.client.sign.IssueSignRequest

data class IssueSignRequestDto(
    override val type: Int = TxType.ISSUE.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
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
                reissuable = reissuable,
            )
    }
}
