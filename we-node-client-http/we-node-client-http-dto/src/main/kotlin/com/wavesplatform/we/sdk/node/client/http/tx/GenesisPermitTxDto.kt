package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.RoleConstants.fromRoleDtoToDomain
import com.wavesplatform.we.sdk.node.client.http.RoleConstants.toDto
import com.wavesplatform.we.sdk.node.client.tx.GenesisPermitTx

data class GenesisPermitTxDto(
    override val id: String,
    override val type: Int = TxType.GENESIS_PERMIT.code,
    val target: String,
    val role: String,
    val fee: Long,
    override val timestamp: Long,
    val signature: String,
) : TxDto {
    companion object {
        @JvmStatic
        fun GenesisPermitTx.toDto(): GenesisPermitTxDto =
            GenesisPermitTxDto(
                id = id.asBase58String(),
                target = target.asBase58String(),
                role = role.toDto(),
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                signature = signature.asBase58String(),
            )

        @JvmStatic
        fun GenesisPermitTxDto.toDomain(): GenesisPermitTx =
            GenesisPermitTx(
                id = TxId.fromBase58(id),
                target = Address.fromBase58(target),
                role = role.fromRoleDtoToDomain(),
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                signature = Signature.fromBase58(signature),
            )

        internal fun toDomainInternal(tx: GenesisPermitTxDto): GenesisPermitTx =
            tx.toDomain()
    }
}
