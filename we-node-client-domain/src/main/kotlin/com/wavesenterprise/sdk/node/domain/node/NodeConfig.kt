package com.wavesenterprise.sdk.node.domain.node

import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.TxCount
import com.wavesenterprise.sdk.node.domain.TxType
import java.time.Duration

data class NodeConfig(
    val version: NodeVersion,
    val cryptoType: CryptoType,
    val chainId: ChainId,
    val consensus: ConsensusType,
    val minimumFee: Map<TxType, Fee>,
    val additionalFee: Map<TxType, Fee>,
    val maxTxsInMicroBlock: TxCount,
    val minMicroBlockAge: Duration,
    val microBlockInterval: Duration,
    val blockTiming: BlockTiming,
)
