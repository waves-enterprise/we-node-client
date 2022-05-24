package com.wavesenterprise.sdk.node.domain.http.tx

import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.sdk.node.domain.tx.ExecutableTx
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx

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
