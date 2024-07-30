package com.wavesenterprise.sdk.node.client.grpc.mapper.event

import com.wavesenterprise.protobuf.service.messagebroker.MessagebrokerBlockchainEvent
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.TxMapper.domain
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
import com.wavesenterprise.sdk.node.domain.event.BlockchainEvent

object BlockchainEventMapper {

    @JvmStatic
    fun MessagebrokerBlockchainEvent.BlockchainEvent.domain(): BlockchainEvent =
        domainInternal(this)

    @JvmStatic
    internal fun domainInternal(blockchainEvent: MessagebrokerBlockchainEvent.BlockchainEvent): BlockchainEvent =
        with(blockchainEvent) {
            when {
                hasMicroBlockAppended() -> with(blockchainEvent.microBlockAppended) {
                    BlockchainEvent.MicroBlockAppended(
                        txs = txsList.map {
                            it.domain()
                        },
                    )
                }

                hasBlockAppended() -> with(blockchainEvent.blockAppended) {
                    BlockchainEvent.BlockAppended(
                        signature = Signature.fromByteArray(blockSignature.toByteArray()),
                        reference = BlockReference(reference.toByteArray()),
                        txIds = txIdsList.map { TxId.fromBase58(it.toString()) },
                        miner = Address.fromByteArray(minerAddress.toByteArray()),
                        height = Height.fromLong(height),
                        version = BlockVersion.fromInt(version),
                        timestamp = Timestamp.fromUtcTimestamp(timestamp),
                        fee = Fee.fromLong(fee),
                        blockSize = DataSize(blockSize.toLong()),
                        features = featuresList.map { Feature.fromInt(it) },
                    )
                }

                hasRollbackCompleted() -> with(blockchainEvent.rollbackCompleted) {
                    BlockchainEvent.RollbackCompleted(
                        returnToBlockSignature = Signature.fromByteArray(returnToBlockSignature.toByteArray()),
                        rollbackTxIds = rollbackTxIdsList.map { TxId.fromBase58(it.toString()) },
                    )
                }

                hasAppendedBlockHistory() -> with(blockchainEvent.appendedBlockHistory) {
                    BlockchainEvent.AppendedBlockHistory(
                        signature = Signature.fromByteArray(blockSignature.toByteArray()),
                        reference = BlockReference(reference.toByteArray()),
                        txs = txsList.map { it.domain() },
                        miner = Address.fromByteArray(minerAddress.toByteArray()),
                        height = Height.fromLong(height),
                        version = BlockVersion.fromInt(version),
                        timestamp = Timestamp.fromUtcTimestamp(timestamp),
                        fee = Fee.fromLong(fee),
                        blockSize = DataSize(blockSize.toLong()),
                        features = featuresList.map { Feature.fromInt(it) },
                    )
                }

                else -> error("No mapping for block $this")
            }
        }
}
