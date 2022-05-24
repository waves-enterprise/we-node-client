package com.wavesenterprise.sdk.node.domain.http.tx

import com.wavesenterprise.sdk.node.domain.tx.AtomicSignInnerTx
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreatePolicyTx
import com.wavesenterprise.sdk.node.domain.tx.DisableContractTx
import com.wavesenterprise.sdk.node.domain.tx.PermitTx
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.sdk.node.domain.tx.TransferTx
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx
import com.wavesenterprise.sdk.node.domain.tx.UpdatePolicyTx

sealed interface AtomicSignInnerTxDto : TxDto {
    companion object {
        @JvmStatic
        fun AtomicSignInnerTx<*>.toDto(): AtomicSignInnerTxDto =
            when (val tx = this) {
                is CallContractTx -> CallContractTxDto.toDtoInternal(tx)
                is CreateContractTx -> CreateContractTxDto.toDtoInternal(tx)
                is CreatePolicyTx -> CreatePolicyTxDto.toDtoInternal(tx)
                is DisableContractTx -> DisableContractTxDto.toDtoInternal(tx)
                is PermitTx -> PermitTxDto.toDtoInternal(tx)
                is PolicyDataHashTx -> PolicyDataHashTxDto.toDtoInternal(tx)
                is TransferTx -> TransferTxDto.toDtoInternal(tx)
                is UpdateContractTx -> UpdateContractTxDto.toDtoInternal(tx)
                is UpdatePolicyTx -> UpdatePolicyTxDto.toDtoInternal(tx)
            }
    }
}
