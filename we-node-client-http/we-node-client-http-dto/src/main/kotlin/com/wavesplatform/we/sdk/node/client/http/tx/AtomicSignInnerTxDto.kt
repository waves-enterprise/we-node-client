package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.tx.AtomicSignInnerTx
import com.wavesplatform.we.sdk.node.client.tx.CallContractTx
import com.wavesplatform.we.sdk.node.client.tx.CreateContractTx
import com.wavesplatform.we.sdk.node.client.tx.CreatePolicyTx
import com.wavesplatform.we.sdk.node.client.tx.DisableContractTx
import com.wavesplatform.we.sdk.node.client.tx.PermitTx
import com.wavesplatform.we.sdk.node.client.tx.PolicyDataHashTx
import com.wavesplatform.we.sdk.node.client.tx.TransferTx
import com.wavesplatform.we.sdk.node.client.tx.UpdateContractTx
import com.wavesplatform.we.sdk.node.client.tx.UpdatePolicyTx

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
