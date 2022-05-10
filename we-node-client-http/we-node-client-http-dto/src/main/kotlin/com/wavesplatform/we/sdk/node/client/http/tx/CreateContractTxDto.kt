package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.ContractImage
import com.wavesplatform.we.sdk.node.client.ContractName
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.ContractApiVersionDto
import com.wavesplatform.we.sdk.node.client.http.ContractApiVersionDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.ContractApiVersionDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.DataEntryDto
import com.wavesplatform.we.sdk.node.client.http.DataEntryDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.DataEntryDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.ValidationPolicyDto
import com.wavesplatform.we.sdk.node.client.http.ValidationPolicyDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.ValidationPolicyDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.tx.CreateContractTx

data class CreateContractTxDto(
    override val id: String,
    override val type: Int = TxType.CREATE_CONTRACT.code,
    val senderPublicKey: String,
    val image: String,
    val imageHash: String,
    val contractName: String,
    val params: List<DataEntryDto>,
    val fee: Long,
    override val timestamp: Long,
    val feeAssetId: String?,
    val atomicBadge: AtomicBadgeDto?,
    val validationPolicy: ValidationPolicyDto?,
    val apiVersion: ContractApiVersionDto?,
    val proofs: List<String>?,
    val sender: String,
    val version: Int,
) : TxDto, ExecutableTxDto, AtomicInnerTxDto {
    companion object {
        @JvmStatic
        fun CreateContractTx.toDto(): CreateContractTxDto =
            CreateContractTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                image = image.value,
                imageHash = imageHash.asHexString(),
                contractName = contractName.value,
                params = params.map { it.toDto() },
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                feeAssetId = feeAssetId?.asBase58String(),
                atomicBadge = atomicBadge?.toDto(),
                validationPolicy = validationPolicy?.toDto(),
                apiVersion = apiVersion?.toDto(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun CreateContractTxDto.toDomain(): CreateContractTx =
            CreateContractTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                image = ContractImage(image),
                imageHash = Hash.fromHexString(imageHash),
                contractName = ContractName(contractName),
                params = params.map { it.toDomain() },
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                feeAssetId = feeAssetId?.let { FeeAssetId.fromBase58(it) },
                atomicBadge = atomicBadge?.toDomain(),
                validationPolicy = validationPolicy?.toDomain(),
                apiVersion = apiVersion?.toDomain(),
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDtoInternal(tx: CreateContractTx): CreateContractTxDto =
            tx.toDto()

        internal fun toDomainInternal(tx: CreateContractTxDto): CreateContractTx =
            tx.toDomain()
    }
}
