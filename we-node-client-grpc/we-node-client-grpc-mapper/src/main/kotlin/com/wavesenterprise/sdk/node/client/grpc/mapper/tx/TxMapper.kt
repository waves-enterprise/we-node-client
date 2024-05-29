package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.AtomicTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.BurnTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.CallContractTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.CreateAliasTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.CreateContractTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.CreatePolicyTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.DisableContractTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.ExecutedContractTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.GenesisPermitTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.GenesisRegisterNodeTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.GenesisTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.IssueTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.LeaseCancelTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.LeaseTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.PermitTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.PolicyDataHashTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.RegisterNodeTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.ReissueTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.SponsorFeeTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.TransferTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.TxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.UpdateContractTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.UpdatePolicyTxMapper.domain
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.sdk.node.domain.tx.BurnTx
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateAliasTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreatePolicyTx
import com.wavesenterprise.sdk.node.domain.tx.DataTx
import com.wavesenterprise.sdk.node.domain.tx.DisableContractTx
import com.wavesenterprise.sdk.node.domain.tx.ExecutedContractTx
import com.wavesenterprise.sdk.node.domain.tx.GenesisPermitTx
import com.wavesenterprise.sdk.node.domain.tx.GenesisRegisterNodeTx
import com.wavesenterprise.sdk.node.domain.tx.GenesisTx
import com.wavesenterprise.sdk.node.domain.tx.IssueTx
import com.wavesenterprise.sdk.node.domain.tx.LeaseCancelTx
import com.wavesenterprise.sdk.node.domain.tx.LeaseTx
import com.wavesenterprise.sdk.node.domain.tx.MassTransferTx
import com.wavesenterprise.sdk.node.domain.tx.PermitTx
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.sdk.node.domain.tx.RegisterNodeTx
import com.wavesenterprise.sdk.node.domain.tx.ReissueTx
import com.wavesenterprise.sdk.node.domain.tx.SetAssetScriptTx
import com.wavesenterprise.sdk.node.domain.tx.SetScriptTx
import com.wavesenterprise.sdk.node.domain.tx.SponsorFeeTx
import com.wavesenterprise.sdk.node.domain.tx.TransferTx
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx
import com.wavesenterprise.sdk.node.domain.tx.UpdatePolicyTx
import com.wavesenterprise.transaction.protobuf.TransactionOuterClass.Transaction
import com.wavesenterprise.transaction.protobuf.transaction

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
                is CreateAliasTx -> createAliasTransaction = CreateAliasTxMapper.dtoInternal(tx)
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
            -> TODO("Not yet implemented")
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
            -> TODO("Not yet implemented")
            Transaction.TransactionCase.DATA_TRANSACTION,
            -> TODO("Not yet implemented")
            Transaction.TransactionCase.TRANSFER_TRANSACTION,
            -> transferTransaction.domain(version)
            Transaction.TransactionCase.MASS_TRANSFER_TRANSACTION,
            -> TODO("Not yet implemented")
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
            -> TODO("Not yet implemented")
            Transaction.TransactionCase.ATOMIC_TRANSACTION,
            -> atomicTransaction.domain(version)
            Transaction.TransactionCase.TRANSACTION_NOT_SET,
            null,
            -> error("Transaction not set")
        }
    }
}
