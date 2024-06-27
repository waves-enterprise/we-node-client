package com.wavesenterprise.sdk.node.domain.event

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.BlockReference
import com.wavesenterprise.sdk.node.domain.BlockVersion
import com.wavesenterprise.sdk.node.domain.DataSize
import com.wavesenterprise.sdk.node.domain.Feature
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.tx.Tx

sealed interface BlockchainEvent {
    data class MicroBlockAppended(
        val txs: List<Tx>,
    ) : BlockchainEvent

    data class BlockAppended(
        val signature: Signature,
        val reference: BlockReference,
        val txIds: List<TxId>,
        val miner: Address,
        val height: Height,
        val version: BlockVersion,
        val timestamp: Timestamp,
        val fee: Fee,
        val blockSize: DataSize,
        val features: List<Feature>,
    ) : BlockchainEvent

    data class RollbackCompleted(
        val returnToBlockSignature: Signature,
        val rollbackTxIds: List<TxId>,
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
        val features: List<Feature>,
    ) : BlockchainEvent
}
