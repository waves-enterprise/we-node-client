package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.TransactionOuterClass.Transaction
import com.wavesenterprise.transaction.protobuf.transaction
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.AtomicTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.CallContractTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.CreateContractTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.CreatePolicyTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.DisableContractTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.ExecutedContractTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.PermitTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.PolicyDataHashTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.TransferTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.TxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.UpdateContractTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.UpdatePolicyTxMapper.domain
import com.wavesplatform.we.sdk.node.client.tx.AtomicTx
import com.wavesplatform.we.sdk.node.client.tx.BurnTx
import com.wavesplatform.we.sdk.node.client.tx.CallContractTx
import com.wavesplatform.we.sdk.node.client.tx.CreateAliasTx
import com.wavesplatform.we.sdk.node.client.tx.CreateContractTx
import com.wavesplatform.we.sdk.node.client.tx.CreatePolicyTx
import com.wavesplatform.we.sdk.node.client.tx.DataTx
import com.wavesplatform.we.sdk.node.client.tx.DisableContractTx
import com.wavesplatform.we.sdk.node.client.tx.ExecutedContractTx
import com.wavesplatform.we.sdk.node.client.tx.GenesisPermitTx
import com.wavesplatform.we.sdk.node.client.tx.GenesisRegisterNodeTx
import com.wavesplatform.we.sdk.node.client.tx.GenesisTx
import com.wavesplatform.we.sdk.node.client.tx.IssueTx
import com.wavesplatform.we.sdk.node.client.tx.LeaseCancelTx
import com.wavesplatform.we.sdk.node.client.tx.LeaseTx
import com.wavesplatform.we.sdk.node.client.tx.MassTransferTx
import com.wavesplatform.we.sdk.node.client.tx.PermitTx
import com.wavesplatform.we.sdk.node.client.tx.PolicyDataHashTx
import com.wavesplatform.we.sdk.node.client.tx.RegisterNodeTx
import com.wavesplatform.we.sdk.node.client.tx.ReissueTx
import com.wavesplatform.we.sdk.node.client.tx.SetAssetScriptTx
import com.wavesplatform.we.sdk.node.client.tx.SetScriptTx
import com.wavesplatform.we.sdk.node.client.tx.SponsorFeeTx
import com.wavesplatform.we.sdk.node.client.tx.TransferTx
import com.wavesplatform.we.sdk.node.client.tx.Tx
import com.wavesplatform.we.sdk.node.client.tx.UpdateContractTx
import com.wavesplatform.we.sdk.node.client.tx.UpdatePolicyTx

object TxMapper {
    @JvmStatic
    fun Tx.dto(): Transaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: Tx): Transaction =
        transaction {
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
                is AtomicTx -> atomicTransaction = AtomicTxMapper.dtoInternal(tx)
                is BurnTx -> burnTransaction = BurnTxMapper.dtoInternal(tx)
                is CreateAliasTx -> permitTransaction = CreateAliasTxMapper.dtoInternal(tx)
                is DataTx -> dataTransaction = DataTxMapper.dtoInternal(tx)
                is GenesisPermitTx -> genesisPermitTransaction = GenesisPermitTxMapper.dtoInternal(tx)
                is GenesisRegisterNodeTx -> genesisRegisterNodeTransaction = GenesisRegisterNodeTxMapper.dtoInternal(tx)
                is GenesisTx -> genesisTransaction = GenesisTxMapper.dtoInternal(tx)
                is IssueTx -> issueTransaction = IssueTxMapper.dtoInternal(tx)
                is LeaseCancelTx -> leaseCancelTransaction = LeaseCancelTxMapper.dtoInternal(tx)
                is LeaseTx -> leaseTransaction = LeaseTxMapper.dtoInternal(tx)
                is MassTransferTx -> massTransferTransaction = MassTransferTxMapper.dtoInternal(tx)
                is RegisterNodeTx -> registerNodeTransaction = RegisterNodeTxMapper.dtoInternal(tx)
                is ReissueTx -> reissueTransaction = ReissueTxMapper.dtoInternal(tx)
                is SetAssetScriptTx -> setAssetScriptTransaction = SetAssetScriptTxMapper.dtoInternal(tx)
                is SetScriptTx -> setScriptTransaction = SetScriptTxMapper.dtoInternal(tx)
                is SponsorFeeTx -> sponsorFeeTransaction = SponsorFeeTxMapper.dtoInternal(tx)
            }
        }

    @JvmStatic
    fun Transaction.domain(): Tx {
        val version = TxVersion(version)
        return when (transactionCase) {
            Transaction.TransactionCase.GENESIS_TRANSACTION,
            -> genesisTransaction.domain(version)
            Transaction.TransactionCase.GENESIS_PERMIT_TRANSACTION,
            -> genesisPermitTransaction.domain(version)
            Transaction.TransactionCase.GENESIS_REGISTER_NODE_TRANSACTION,
            -> genesisRegisterNodeTransaction.domain(version)
            Transaction.TransactionCase.REGISTER_NODE_TRANSACTION,
            -> registerNodeTransaction.domain(version)
            Transaction.TransactionCase.CREATE_ALIAS_TRANSACTION,
            -> createAliasTransaction.domain(version)
            Transaction.TransactionCase.ISSUE_TRANSACTION,
            -> issueTransaction.domain(version)
            Transaction.TransactionCase.REISSUE_TRANSACTION,
            -> reissueTransaction.domain(version)
            Transaction.TransactionCase.BURN_TRANSACTION,
            -> burnTransaction.domain(version)
            Transaction.TransactionCase.LEASE_TRANSACTION,
            -> leaseTransaction.domain(version)
            Transaction.TransactionCase.LEASE_CANCEL_TRANSACTION,
            -> leaseCancelTransaction.domain(version)
            Transaction.TransactionCase.SPONSOR_FEE_TRANSACTION,
            -> sponsorFeeTransaction.domain(version)
            Transaction.TransactionCase.SET_ASSET_SCRIPT_TRANSACTION,
            -> setAssetScriptTransaction.domain(version)
            Transaction.TransactionCase.DATA_TRANSACTION,
            -> dataTransaction.domain(version)
            Transaction.TransactionCase.TRANSFER_TRANSACTION,
            -> transferTransaction.domain(version)
            Transaction.TransactionCase.MASS_TRANSFER_TRANSACTION,
            -> massTransferTransaction.domain(version)
            Transaction.TransactionCase.PERMIT_TRANSACTION,
            -> permitTransaction.domain(version)
            Transaction.TransactionCase.CREATE_POLICY_TRANSACTION,
            -> createPolicyTransaction.domain(version)
            Transaction.TransactionCase.UPDATE_POLICY_TRANSACTION,
            -> updatePolicyTransaction.domain(version)
            Transaction.TransactionCase.POLICY_DATA_HASH_TRANSACTION,
            -> policyDataHashTransaction.domain(version)
            Transaction.TransactionCase.CREATE_CONTRACT_TRANSACTION,
            -> createContractTransaction.domain(version)
            Transaction.TransactionCase.CALL_CONTRACT_TRANSACTION,
            -> callContractTransaction.domain(version)
            Transaction.TransactionCase.EXECUTED_CONTRACT_TRANSACTION,
            -> executedContractTransaction.domain(version)
            Transaction.TransactionCase.DISABLE_CONTRACT_TRANSACTION,
            -> disableContractTransaction.domain(version)
            Transaction.TransactionCase.UPDATE_CONTRACT_TRANSACTION,
            -> updateContractTransaction.domain(version)
            Transaction.TransactionCase.SET_SCRIPT_TRANSACTION,
            -> setScriptTransaction.domain(version)
            Transaction.TransactionCase.ATOMIC_TRANSACTION,
            -> atomicTransaction.domain(version)
            Transaction.TransactionCase.TRANSACTION_NOT_SET,
            null,
            -> error("Transaction not set")
        }
    }
}
