package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.DataEntryDto
import com.wavesplatform.we.sdk.node.client.http.DataEntryDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.CreateContractTxDto
import com.wavesplatform.we.sdk.node.client.sign.CallContractSignRequest

data class CallContractSignRequestDto(
    override val type: Int = TxType.CALL_CONTRACT.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val feeAssetId: String? = null,
    val contractId: String,
    val params: List<DataEntryDto>,
    val atomicBadge: AtomicBadgeDto? = null,
) : SignRequestDto<CreateContractTxDto> {
    companion object {
        @JvmStatic
        fun CallContractSignRequest.toDto(): CallContractSignRequestDto =
            CallContractSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                contractId = contractId.asBase58String(),
                params = params.map { it.toDto() },
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
