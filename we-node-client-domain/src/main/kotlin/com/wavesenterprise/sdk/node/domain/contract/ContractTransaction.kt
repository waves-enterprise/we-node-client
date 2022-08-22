package com.wavesenterprise.sdk.node.domain.contract

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion

sealed interface ContractTransaction {
    val id: TxId
    val type: TxType
    val sender: Address
    val senderPublicKey: PublicKey
    val contractId: ContractId
    val params: List<DataEntry>
    val fee: Fee
    val version: TxVersion
    val proof: Signature?
    val timestamp: Timestamp
    val feeAssetId: AssetId?
}

data class CreateContractTransaction(
    override val id: TxId,
    override val type: TxType,
    override val sender: Address,
    override val senderPublicKey: PublicKey,
    override val contractId: ContractId,
    override val params: List<DataEntry>,
    override val fee: Fee,
    override val version: TxVersion,
    override val proof: Signature?,
    override val timestamp: Timestamp,
    override val feeAssetId: AssetId?,
    val image: ContractImage,
    val imageHash: ContractImageHash,
    val contractName: ContractName,
) : ContractTransaction

data class CallContractTransaction(
    override val id: TxId,
    override val type: TxType,
    override val sender: Address,
    override val senderPublicKey: PublicKey,
    override val contractId: ContractId,
    override val params: List<DataEntry>,
    override val fee: Fee,
    override val version: TxVersion,
    override val proof: Signature?,
    override val timestamp: Timestamp,
    override val feeAssetId: AssetId?,
    val contractVersion: ContractVersion,
) : ContractTransaction
