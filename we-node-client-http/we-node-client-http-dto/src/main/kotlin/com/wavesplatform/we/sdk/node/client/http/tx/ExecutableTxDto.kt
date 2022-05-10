package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.tx.CallContractTx
import com.wavesplatform.we.sdk.node.client.tx.CreateContractTx
import com.wavesplatform.we.sdk.node.client.tx.ExecutableTx
import com.wavesplatform.we.sdk.node.client.tx.UpdateContractTx

sealed interface ExecutableTxDto {
    companion object {
        @JvmStatic
        fun ExecutableTx.toDto(): ExecutableTxDto =
            when (val tx = this) {
                is CallContractTx -> CallContractTxDto.toDtoInternal(tx)
                is CreateContractTx -> CreateContractTxDto.toDtoInternal(tx)
                is UpdateContractTx -> UpdateContractTxDto.toDtoInternal(tx)
            }

        @JvmStatic
        fun ExecutableTxDto.toDomain(): ExecutableTx =
            when (val tx = this) {
                is CallContractTxDto -> CallContractTxDto.toDomainInternal(tx)
                is CreateContractTxDto -> CreateContractTxDto.toDomainInternal(tx)
                is UpdateContractTxDto -> UpdateContractTxDto.toDomainInternal(tx)
            }
    }
}
