package com.wavesenterprise.sdk.node.domain.converter

import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.contract.CallContractTransaction
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.CreateContractTransaction
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.ContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx

fun ContractTx.toContractTransaction() = when (this) {
    is CreateContractTx -> CreateContractTransaction(
        id = id,
        type = TxType.CREATE_CONTRACT,
        sender = senderAddress,
        senderPublicKey = senderPublicKey,
        contractId = ContractId(id),
        params = params,
        fee = fee,
        version = version,
        proof = proofs?.first(),
        timestamp = timestamp,
        feeAssetId = feeAssetId?.toContractTransaction(),
        image = image,
        imageHash = imageHash,
        contractName = contractName,
    )
    is CallContractTx -> CallContractTransaction(
        id = id,
        type = TxType.CALL_CONTRACT,
        sender = senderAddress,
        senderPublicKey = senderPublicKey,
        contractId = contractId,
        params = params,
        fee = fee,
        version = version,
        proof = proofs?.first(),
        timestamp = timestamp,
        feeAssetId = feeAssetId?.toContractTransaction(),
        contractVersion = contractVersion,
    )
}

fun FeeAssetId.toContractTransaction() = AssetId(
    bytes = this.txId.bytes,
)
