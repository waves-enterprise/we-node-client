package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.OpTypeConstants.toDto
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.PermitTxDto
import com.wavesplatform.we.sdk.node.client.sign.PermitSignRequest

data class PermitSignRequestDto(
    override val type: Int = TxType.PERMIT.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val target: String,
    val opType: String,
    val dueTimestamp: Long,
    val atomicBadge: AtomicBadgeDto?,
) : SignRequestDto<PermitTxDto> {
    companion object {
        @JvmStatic
        fun PermitSignRequest.toDto(): PermitSignRequestDto =
            PermitSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                target = target.asBase58String(),
                opType = opType.toDto(),
                dueTimestamp = dueTimestamp.utcTimestampMillis,
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
