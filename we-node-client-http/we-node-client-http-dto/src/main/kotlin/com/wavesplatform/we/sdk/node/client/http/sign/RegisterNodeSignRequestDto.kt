package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.OpTypeConstants.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.RegisterNodeTxDto
import com.wavesplatform.we.sdk.node.client.sign.RegisterNodeSignRequest

data class RegisterNodeSignRequestDto(
    override val type: Int = TxType.REGISTER_NODE.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val opType: String,
    val target: String,
    val targetPublicKey: String,
    val nodeName: String,
) : SignRequestDto<RegisterNodeTxDto> {
    companion object {
        @JvmStatic
        fun RegisterNodeSignRequest.toDto(): RegisterNodeSignRequestDto =
            RegisterNodeSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                opType = opType.toDto(),
                target = target.asBase58String(),
                targetPublicKey = targetPublicKey.asBase58String(),
                nodeName = nodeName.value,
            )
    }
}
