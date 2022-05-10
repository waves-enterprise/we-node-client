package com.wavesplatform.we.sdk.node.client.node

import com.wavesplatform.we.sdk.node.client.ChainId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.TxCount
import com.wavesplatform.we.sdk.node.client.TxType
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
