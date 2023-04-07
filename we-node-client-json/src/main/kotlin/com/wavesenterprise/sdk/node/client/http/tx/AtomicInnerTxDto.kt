package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.domain.tx.AtomicInnerTx
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreatePolicyTx
import com.wavesenterprise.sdk.node.domain.tx.DisableContractTx
import com.wavesenterprise.sdk.node.domain.tx.ExecutedContractTx
import com.wavesenterprise.sdk.node.domain.tx.PermitTx
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.sdk.node.domain.tx.TransferTx
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx
import com.wavesenterprise.sdk.node.domain.tx.UpdatePolicyTx

sealed interface AtomicInnerTxDto : TxDto {
    companion object {
        @JvmStatic
        fun AtomicInnerTx.toDto(): AtomicInnerTxDto =
            when (val tx = this) {
                is CallContractTx -> CallContractTxDto.toDtoInternal(tx)
                is CreateContractTx -> CreateContractTxDto.toDtoInternal(tx)
                is CreatePolicyTx -> CreatePolicyTxDto.toDtoInternal(tx)
                is DisableContractTx -> DisableContractTxDto.toDtoInternal(tx)
                is ExecutedContractTx -> ExecutedContractTxDto.toDtoInternal(tx)
                is PermitTx -> PermitTxDto.toDtoInternal(tx)
                is PolicyDataHashTx -> PolicyDataHashTxDto.toDtoInternal(tx)
                is TransferTx -> TransferTxDto.toDtoInternal(tx)
                is UpdateContractTx -> UpdateContractTxDto.toDtoInternal(tx)
                is UpdatePolicyTx -> UpdatePolicyTxDto.toDtoInternal(tx)
            }

        @JvmStatic
        fun AtomicInnerTxDto.toDomain(): AtomicInnerTx =
            when (val tx = this) {
                is CallContractTxDto -> CallContractTxDto.toDomainInternal(tx)
                is CreateContractTxDto -> CreateContractTxDto.toDomainInternal(tx)
                is CreatePolicyTxDto -> CreatePolicyTxDto.toDomainInternal(tx)
                is DisableContractTxDto -> DisableContractTxDto.toDomainInternal(tx)
                is ExecutedContractTxDto -> ExecutedContractTxDto.toDomainInternal(tx)
                is PermitTxDto -> PermitTxDto.toDomainInternal(tx)
                is PolicyDataHashTxDto -> PolicyDataHashTxDto.toDomainInternal(tx)
                is TransferTxDto -> TransferTxDto.toDomainInternal(tx)
                is UpdateContractTxDto -> UpdateContractTxDto.toDomainInternal(tx)
                is UpdatePolicyTxDto -> UpdatePolicyTxDto.toDomainInternal(tx)
            }
    }
}
