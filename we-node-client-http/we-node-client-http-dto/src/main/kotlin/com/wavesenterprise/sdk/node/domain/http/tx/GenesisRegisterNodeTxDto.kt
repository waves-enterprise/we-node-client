package com.wavesenterprise.sdk.node.domain.http.tx

import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.GenesisRegisterNodeTx

data class GenesisRegisterNodeTxDto(
    override val id: String,
    override val type: Int = TxType.GENESIS_REGISTER_NODE.code,
    val targetPublicKey: String,
    val fee: Long,
    override val timestamp: Long,
    val signature: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto {
    companion object {
        @JvmStatic
        fun GenesisRegisterNodeTx.toDto(): GenesisRegisterNodeTxDto =
            GenesisRegisterNodeTxDto(
                id = id.asBase58String(),
                targetPublicKey = targetPublicKey.asBase58String(),
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                signature = signature.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun GenesisRegisterNodeTxDto.toDomain(): GenesisRegisterNodeTx =
            GenesisRegisterNodeTx(
                id = TxId.fromBase58(id),
                targetPublicKey = PublicKey.fromBase58(targetPublicKey),
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                signature = Signature.fromBase58(signature),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: GenesisRegisterNodeTxDto): GenesisRegisterNodeTx =
            tx.toDomain()
    }
}
