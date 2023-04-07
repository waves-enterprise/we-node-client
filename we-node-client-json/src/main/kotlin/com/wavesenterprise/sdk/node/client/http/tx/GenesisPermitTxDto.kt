package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.client.http.RoleConstants.fromRoleDtoToDomain
import com.wavesenterprise.sdk.node.client.http.RoleConstants.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.GenesisPermitTx

data class GenesisPermitTxDto(
    override val id: String,
    override val type: Int = TxType.GENESIS_PERMIT.code,
    val target: String,
    val role: String,
    val fee: Long,
    override val timestamp: Long,
    val signature: String,
    override val version: Int,
    override val height: Long? = null,
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
                version = version.value,
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
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: GenesisPermitTxDto): GenesisPermitTx =
            tx.toDomain()
    }
}
