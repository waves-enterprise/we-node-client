package com.wavesenterprise.sdk.node.client.http.node

import com.wavesenterprise.sdk.node.client.http.node.BlockTimingDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.TxCount
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.node.ConsensusType
import com.wavesenterprise.sdk.node.domain.node.CryptoType
import com.wavesenterprise.sdk.node.domain.node.NodeConfig
import com.wavesenterprise.sdk.node.domain.node.NodeVersion
import java.time.Duration

data class NodeConfigDto(
    val version: String,
    val cryptoType: String,
    val chainId: Byte,
    val consensus: String,
    val minimumFee: Map<String, Long>,
    val additionalFee: Map<String, Long>,
    val maxTxsInMicroBlock: Int,
    val minMicroBlockAge: Duration,
    val microBlockInterval: Duration,
    val blockTiming: BlockTimingDto,
) {
    companion object {
        @JvmStatic
        fun NodeConfigDto.toDomain() =
            NodeConfig(
                version = NodeVersion(version),
                cryptoType = CryptoType.valueOf(cryptoType),
                chainId = ChainId(chainId),
                consensus = ConsensusType.valueOf(consensus),
                minimumFee = minimumFee.entries.associate {
                    TxType.valueOf(it.key) to Fee.fromLong(it.value)
                },
                additionalFee = additionalFee.entries.associate {
                    TxType.valueOf(it.key) to Fee.fromLong(it.value)
                },
                maxTxsInMicroBlock = TxCount.fromInt(maxTxsInMicroBlock),
                minMicroBlockAge = minMicroBlockAge,
                microBlockInterval = microBlockInterval,
                blockTiming = blockTiming.toDomain(),
            )
    }
}
