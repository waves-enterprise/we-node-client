package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.ExecutableTransaction
import com.wavesenterprise.transaction.protobuf.ExecutableTransaction.TransactionCase
import com.wavesenterprise.transaction.protobuf.executableTransaction
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.CallContractTx
import com.wavesplatform.we.sdk.node.client.tx.CreateContractTx
import com.wavesplatform.we.sdk.node.client.tx.ExecutableTx
import com.wavesplatform.we.sdk.node.client.tx.UpdateContractTx

object ExecutableTxMapper {
    @JvmStatic
    fun ExecutableTx.dto(): ExecutableTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: ExecutableTx): ExecutableTransaction =
        executableTransaction {
            version = tx.version.value
            when (tx) {
                is CallContractTx -> callContractTransaction = CallContractTxMapper.dtoInternal(tx)
                is CreateContractTx -> createContractTransaction = CreateContractTxMapper.dtoInternal(tx)
                is UpdateContractTx -> updateContractTransaction = UpdateContractTxMapper.dtoInternal(tx)
            }
        }

    @JvmStatic
    fun ExecutableTransaction.domain(): ExecutableTx =
        domainInternal(this)

    @JvmStatic
    internal fun domainInternal(tx: ExecutableTransaction): ExecutableTx {
        val version = TxVersion(tx.version)
        return when (tx.transactionCase) {
            TransactionCase.CREATE_CONTRACT_TRANSACTION
            -> CreateContractTxMapper.domainInternal(tx.createContractTransaction, version)
            TransactionCase.CALL_CONTRACT_TRANSACTION
            -> CallContractTxMapper.domainInternal(tx.callContractTransaction, version)
            TransactionCase.UPDATE_CONTRACT_TRANSACTION
            -> UpdateContractTxMapper.domainInternal(tx.updateContractTransaction, version)
            TransactionCase.TRANSACTION_NOT_SET, null -> error("Transaction not set")
        }
    }
}
