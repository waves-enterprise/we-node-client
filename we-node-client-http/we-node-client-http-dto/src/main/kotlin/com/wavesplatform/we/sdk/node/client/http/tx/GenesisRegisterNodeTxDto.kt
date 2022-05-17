package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.GenesisRegisterNodeTx

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
