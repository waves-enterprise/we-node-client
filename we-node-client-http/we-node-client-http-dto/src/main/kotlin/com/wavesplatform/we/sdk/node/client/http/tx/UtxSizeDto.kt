package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.DataSize
import com.wavesplatform.we.sdk.node.client.TxCount
import com.wavesplatform.we.sdk.node.client.tx.UtxSize

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
