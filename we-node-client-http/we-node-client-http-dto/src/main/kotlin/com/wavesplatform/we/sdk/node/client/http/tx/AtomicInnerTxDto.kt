package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.tx.AtomicInnerTx
import com.wavesplatform.we.sdk.node.client.tx.CallContractTx
import com.wavesplatform.we.sdk.node.client.tx.CreateContractTx
import com.wavesplatform.we.sdk.node.client.tx.CreatePolicyTx
import com.wavesplatform.we.sdk.node.client.tx.DisableContractTx
import com.wavesplatform.we.sdk.node.client.tx.ExecutedContractTx
import com.wavesplatform.we.sdk.node.client.tx.PermitTx
import com.wavesplatform.we.sdk.node.client.tx.PolicyDataHashTx
import com.wavesplatform.we.sdk.node.client.tx.TransferTx
import com.wavesplatform.we.sdk.node.client.tx.UpdateContractTx
import com.wavesplatform.we.sdk.node.client.tx.UpdatePolicyTx

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
