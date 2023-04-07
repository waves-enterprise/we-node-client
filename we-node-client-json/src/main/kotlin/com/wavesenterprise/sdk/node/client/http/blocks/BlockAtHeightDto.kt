package com.wavesenterprise.sdk.node.client.http.blocks

import com.fasterxml.jackson.annotation.JsonProperty
import com.wavesenterprise.sdk.node.client.http.blocks.PoaConsensusDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.blocks.PosConsensusDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.TxDto
import com.wavesenterprise.sdk.node.client.http.tx.TxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.BlockVersion
import com.wavesenterprise.sdk.node.domain.Feature
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.blocks.BlockAtHeight
import java.math.BigDecimal

data class BlockAtHeightDto(
    val reference: String,
    @JsonProperty("blocksize")
    val blockSize: Long,
    val features: List<Int> = emptyList(),
    val signature: String,
    val fee: BigDecimal,
    val generator: String,
    val transactionCount: Long,
    val transactions: List<TxDto>,
    val version: Int,
    @JsonProperty("poa-consensus")
    var poaConsensus: PoaConsensusDto? = null,
    @JsonProperty("pos-consensus")
    var posConsensus: PosConsensusDto? = null,
    val timestamp: Long,
    val height: Long,
) {
    companion object {
        @JvmStatic
        fun BlockAtHeightDto.toDomain() = BlockAtHeight(
            reference = reference,
            blockSize = blockSize,
            features = features.map { Feature(it) },
            signature = Signature.fromBase58(signature),
            fee = Fee(fee.longValueExact()),
            generator = generator,
            transactionCount = transactionCount,
            transactions = transactions.map {
                it.toDomain()
            },
            version = BlockVersion(version),
            poaConsensus = poaConsensus?.toDomain(),
            posConsensus = posConsensus?.toDomain(),
            timestamp = timestamp,
            height = Height(height),
        )
    }
}
