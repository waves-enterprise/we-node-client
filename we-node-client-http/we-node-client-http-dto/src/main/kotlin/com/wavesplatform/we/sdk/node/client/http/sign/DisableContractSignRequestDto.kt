package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.DisableContractTxDto
import com.wavesplatform.we.sdk.node.client.sign.DisableContractSignRequest

data class DisableContractSignRequestDto(
    override val type: Int = TxType.DISABLE_CONTRACT.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val contractId: String,
    val feeAssetId: String? = null,
    val atomicBadge: AtomicBadgeDto? = null,
) : SignRequestDto<DisableContractTxDto> {
    companion object {
        @JvmStatic
        fun DisableContractSignRequest.toDto(): DisableContractSignRequestDto =
            DisableContractSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                contractId = contractId.asBase58String(),
                feeAssetId = feeAssetId?.asBase58String(),
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
