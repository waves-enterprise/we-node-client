package com.wavesplatform.we.sdk.node.client.contract

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.DataEntry
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion

sealed interface ContractTransaction {
    val id: TxId
    val type: TxType
    val sender: Address
    val senderPublicKey: PublicKey
    val contractId: ContractId
    val params: List<DataEntry>
    val fee: Fee
    val version: TxVersion
    val proof: Signature
    val timestamp: Timestamp
    val feeAssetId: AssetId
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
    override val proof: Signature,
    override val timestamp: Timestamp,
    override val feeAssetId: AssetId,
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
    override val proof: Signature,
    override val timestamp: Timestamp,
    override val feeAssetId: AssetId,
    val contractVersion: ContractVersion,
) : ContractTransaction
