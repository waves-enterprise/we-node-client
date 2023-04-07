package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.client.http.DataEntryDto
import com.wavesenterprise.sdk.node.client.http.DataEntryDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.DataEntryDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.ValidationProofDto
import com.wavesenterprise.sdk.node.client.http.ValidationProofDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.ValidationProofDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.ExecutableTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.ExecutableTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.ExecutedContractTx

data class ExecutedContractTxDto(
    override val id: String,
    override val type: Int = TxType.EXECUTED_CONTRACT.code,
    val senderPublicKey: String,
    val tx: ExecutableTxDto,
    val results: List<DataEntryDto>,
    val resultsHash: String,
    val validationProofs: List<ValidationProofDto>,
    override val timestamp: Long,
    val proofs: List<String>,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto, AtomicInnerTxDto {
    companion object {
        @JvmStatic
        fun ExecutedContractTx.toDto(): ExecutedContractTxDto =
            ExecutedContractTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                tx = tx.toDto(),
                results = results.map { it.toDto() },
                resultsHash = resultsHash.asHexString(),
                validationProofs = validationProofs.map { it.toDto() },
                timestamp = timestamp.utcTimestampMillis,
                proofs = proofs.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun ExecutedContractTxDto.toDomain(): ExecutedContractTx =
            ExecutedContractTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                tx = tx.toDomain(),
                results = results.map { it.toDomain() },
                resultsHash = Hash.fromHexString(resultsHash),
                validationProofs = validationProofs.map { it.toDomain() },
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                proofs = proofs.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDtoInternal(tx: ExecutedContractTx): ExecutedContractTxDto =
            tx.toDto()

        internal fun toDomainInternal(tx: ExecutedContractTxDto): ExecutedContractTx =
            tx.toDomain()
    }
}
