package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractId.Companion.contractId

sealed interface ContractTx : Tx {
    companion object {
        @JvmStatic
        fun ContractTx.contractId(): ContractId =
            when (this) {
                is CallContractTx -> this.contractId
                is CreateContractTx -> this.id.contractId
            }
    }
}
