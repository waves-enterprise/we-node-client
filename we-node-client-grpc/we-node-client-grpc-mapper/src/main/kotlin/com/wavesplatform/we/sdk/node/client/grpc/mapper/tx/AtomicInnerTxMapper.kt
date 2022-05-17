package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.AtomicInnerTransactionOuterClass.AtomicInnerTransaction
import com.wavesenterprise.transaction.protobuf.AtomicInnerTransactionOuterClass.AtomicInnerTransaction.TransactionCase
import com.wavesenterprise.transaction.protobuf.atomicInnerTransaction
import com.wavesplatform.we.sdk.node.client.TxVersion
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

object AtomicInnerTxMapper {
    @JvmStatic
    fun AtomicInnerTx.dto(): AtomicInnerTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: AtomicInnerTx): AtomicInnerTransaction =
        atomicInnerTransaction {
            version = tx.version.value
            when (tx) {
                is CallContractTx -> callContractTransaction = CallContractTxMapper.dtoInternal(tx)
                is CreateContractTx -> createContractTransaction = CreateContractTxMapper.dtoInternal(tx)
                is CreatePolicyTx -> createPolicyTransaction = CreatePolicyTxMapper.dtoInternal(tx)
                is DisableContractTx -> disableContractTransaction = DisableContractTxMapper.dtoInternal(tx)
                is ExecutedContractTx -> executedContractTransaction = ExecutedContractTxMapper.dtoInternal(tx)
                is PermitTx -> permitTransaction = PermitTxMapper.dtoInternal(tx)
                is PolicyDataHashTx -> policyDataHashTransaction = PolicyDataHashTxMapper.dtoInternal(tx)
                is TransferTx -> transferTransaction = TransferTxMapper.dtoInternal(tx)
                is UpdateContractTx -> updateContractTransaction = UpdateContractTxMapper.dtoInternal(tx)
                is UpdatePolicyTx -> updatePolicyTransaction = UpdatePolicyTxMapper.dtoInternal(tx)
            }
        }

    @JvmStatic
    fun AtomicInnerTransaction.domain(version: TxVersion): AtomicInnerTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: AtomicInnerTransaction, version: TxVersion): AtomicInnerTx =
        when (val case = tx.transactionCase) {
            TransactionCase.CALL_CONTRACT_TRANSACTION,
            -> CallContractTxMapper.domainInternal(tx.callContractTransaction, version)
            TransactionCase.CREATE_CONTRACT_TRANSACTION,
            -> CreateContractTxMapper.domainInternal(tx.createContractTransaction, version)
            TransactionCase.CREATE_POLICY_TRANSACTION,
            -> CreatePolicyTxMapper.domainInternal(tx.createPolicyTransaction, version)
            TransactionCase.DISABLE_CONTRACT_TRANSACTION,
            -> DisableContractTxMapper.domainInternal(tx.disableContractTransaction, version)
            TransactionCase.EXECUTED_CONTRACT_TRANSACTION,
            -> ExecutedContractTxMapper.domainInternal(tx.executedContractTransaction, version)
            TransactionCase.PERMIT_TRANSACTION,
            -> PermitTxMapper.domainInternal(tx.permitTransaction, version)
            TransactionCase.POLICY_DATA_HASH_TRANSACTION,
            -> PolicyDataHashTxMapper.domainInternal(tx.policyDataHashTransaction, version)
            TransactionCase.TRANSFER_TRANSACTION,
            -> TransferTxMapper.domainInternal(tx.transferTransaction, version)
            TransactionCase.UPDATE_CONTRACT_TRANSACTION,
            -> UpdateContractTxMapper.domainInternal(tx.updateContractTransaction, version)
            TransactionCase.UPDATE_POLICY_TRANSACTION,
            -> UpdatePolicyTxMapper.domainInternal(tx.updatePolicyTransaction, version)
            TransactionCase.GENESIS_TRANSACTION,
            TransactionCase.GENESIS_PERMIT_TRANSACTION,
            TransactionCase.GENESIS_REGISTER_NODE_TRANSACTION,
            TransactionCase.REGISTER_NODE_TRANSACTION,
            TransactionCase.CREATE_ALIAS_TRANSACTION,
            TransactionCase.ISSUE_TRANSACTION,
            TransactionCase.REISSUE_TRANSACTION,
            TransactionCase.BURN_TRANSACTION,
            TransactionCase.LEASE_TRANSACTION,
            TransactionCase.LEASE_CANCEL_TRANSACTION,
            TransactionCase.SPONSOR_FEE_TRANSACTION,
            TransactionCase.SET_ASSET_SCRIPT_TRANSACTION,
            TransactionCase.DATA_TRANSACTION,
            TransactionCase.MASS_TRANSFER_TRANSACTION,
            TransactionCase.SET_SCRIPT_TRANSACTION,
            -> error("Unsupported type: ${case.name}")
            TransactionCase.TRANSACTION_NOT_SET, null -> error("Transaction not set")
        }
}
