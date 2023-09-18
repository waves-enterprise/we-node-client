package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxType.ATOMIC
import com.wavesenterprise.sdk.node.domain.TxType.BURN
import com.wavesenterprise.sdk.node.domain.TxType.CALL_CONTRACT
import com.wavesenterprise.sdk.node.domain.TxType.CREATE_ALIAS
import com.wavesenterprise.sdk.node.domain.TxType.CREATE_CONTRACT
import com.wavesenterprise.sdk.node.domain.TxType.CREATE_POLICY
import com.wavesenterprise.sdk.node.domain.TxType.DATA
import com.wavesenterprise.sdk.node.domain.TxType.DISABLE_CONTRACT
import com.wavesenterprise.sdk.node.domain.TxType.EXECUTED_CONTRACT
import com.wavesenterprise.sdk.node.domain.TxType.GENESIS
import com.wavesenterprise.sdk.node.domain.TxType.GENESIS_PERMIT
import com.wavesenterprise.sdk.node.domain.TxType.GENESIS_REGISTER_NODE
import com.wavesenterprise.sdk.node.domain.TxType.ISSUE
import com.wavesenterprise.sdk.node.domain.TxType.LEASE
import com.wavesenterprise.sdk.node.domain.TxType.LEASE_CANCEL
import com.wavesenterprise.sdk.node.domain.TxType.MASS_TRANSFER
import com.wavesenterprise.sdk.node.domain.TxType.PERMIT
import com.wavesenterprise.sdk.node.domain.TxType.POLICY_DATA_HASH
import com.wavesenterprise.sdk.node.domain.TxType.REGISTER_NODE
import com.wavesenterprise.sdk.node.domain.TxType.REISSUE
import com.wavesenterprise.sdk.node.domain.TxType.SET_ASSET_SCRIPT
import com.wavesenterprise.sdk.node.domain.TxType.SET_SCRIPT
import com.wavesenterprise.sdk.node.domain.TxType.SPONSOR_FEE
import com.wavesenterprise.sdk.node.domain.TxType.TRANSFER
import com.wavesenterprise.sdk.node.domain.TxType.UPDATE_CONTRACT
import com.wavesenterprise.sdk.node.domain.TxType.UPDATE_POLICY
import com.wavesenterprise.sdk.node.domain.TxVersion

sealed interface Tx {
    val id: TxId
    val timestamp: Timestamp
    val version: TxVersion

    companion object {

        @JvmStatic
        fun Tx.type(): TxType =
            when (this) {
                is GenesisTx -> GENESIS
                is IssueTx -> ISSUE
                is TransferTx -> TRANSFER
                is ReissueTx -> REISSUE
                is BurnTx -> BURN
                is LeaseTx -> LEASE
                is LeaseCancelTx -> LEASE_CANCEL
                is CreateAliasTx -> CREATE_ALIAS
                is MassTransferTx -> MASS_TRANSFER
                is DataTx -> DATA
                is SetScriptTx -> SET_SCRIPT
                is SponsorFeeTx -> SPONSOR_FEE
                is SetAssetScriptTx -> SET_ASSET_SCRIPT
                is GenesisPermitTx -> GENESIS_PERMIT
                is PermitTx -> PERMIT
                is CreateContractTx -> CREATE_CONTRACT
                is CallContractTx -> CALL_CONTRACT
                is ExecutedContractTx -> EXECUTED_CONTRACT
                is DisableContractTx -> DISABLE_CONTRACT
                is UpdateContractTx -> UPDATE_CONTRACT
                is GenesisRegisterNodeTx -> GENESIS_REGISTER_NODE
                is RegisterNodeTx -> REGISTER_NODE
                is CreatePolicyTx -> CREATE_POLICY
                is UpdatePolicyTx -> UPDATE_POLICY
                is PolicyDataHashTx -> POLICY_DATA_HASH
                is AtomicTx -> ATOMIC
            }
    }
}
