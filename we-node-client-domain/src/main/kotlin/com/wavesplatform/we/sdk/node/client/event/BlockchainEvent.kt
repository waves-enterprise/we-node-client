package com.wavesplatform.we.sdk.node.client.event

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.BlockReference
import com.wavesplatform.we.sdk.node.client.BlockVersion
import com.wavesplatform.we.sdk.node.client.DataSize
import com.wavesplatform.we.sdk.node.client.Feature
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Height
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.tx.Tx

sealed interface BlockchainEvent {
    data class MicroBlockAppended(
        val txs: List<Tx>
    ) : BlockchainEvent

    data class BlockAppended(
        val signature: Signature,
        val reference: String,
        val txIds: List<TxId>,
        val miner: Address,
        val height: Height,
        val version: BlockVersion,
        val timestamp: Timestamp,
        val fee: Fee,
        val blockSize: DataSize,
        val features: List<Feature>
    ) : BlockchainEvent

    data class RollbackCompleted(
        val returnToBlockSignature: Signature,
        val rollbackTxIds: List<TxId>
    ) : BlockchainEvent

    data class AppendedBlockHistory(
        val signature: Signature,
        val reference: BlockReference,
        val txs: List<Tx>,
        val miner: Address,
        val height: Height,
        val version: BlockVersion,
        val timestamp: Timestamp,
        val fee: Fee,
        val blockSize: DataSize,
        val features: List<Feature>
    ) : BlockchainEvent
}
