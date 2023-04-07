package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.domain.DataSize
import com.wavesenterprise.sdk.node.domain.TxCount
import com.wavesenterprise.sdk.node.domain.tx.UtxSize

data class UtxSizeDto(
    val size: Int,
    val sizeInBytes: Long,
) {
    companion object {
        @JvmStatic
        fun UtxSizeDto.toDomain(): UtxSize =
            UtxSize(
                txCount = TxCount(size),
                size = DataSize(sizeInBytes),
            )
    }
}
