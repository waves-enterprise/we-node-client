package com.wavesenterprise.sdk.node.domain.grpc.mapper.contract

import com.wavesenterprise.protobuf.service.contract.ContractTransaction.DataCase
import com.wavesenterprise.protobuf.service.contract.ContractTransaction.DataCase.CREATE_DATA
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.contract.CallContractTransaction
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.contract.ContractName
import com.wavesenterprise.sdk.node.domain.contract.ContractTransaction
import com.wavesenterprise.sdk.node.domain.contract.ContractTransactionResponse
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import com.wavesenterprise.sdk.node.domain.contract.CreateContractTransaction
import com.wavesenterprise.sdk.node.domain.grpc.mapper.DataEntryMapper.domain
import com.wavesenterprise.protobuf.service.contract.ContractTransaction as ProtoContractTransaction
import com.wavesenterprise.protobuf.service.contract.ContractTransactionResponse as ProtoContractTransactionResponse

object ContractTransactionResponseMapper {

    @JvmStatic
    fun ProtoContractTransactionResponse.domain(): ContractTransactionResponse =
        domainInternal(this)

    @JvmStatic
    internal fun domainInternal(
        contractTransactionResponse: ProtoContractTransactionResponse
    ): ContractTransactionResponse = contractTransactionResponse.run {
        ContractTransactionResponse(
            transaction = transaction.domain(),
            authToken = authToken
        )
    }

    @JvmStatic
    fun ProtoContractTransaction.domain(): ContractTransaction =
        domainInternal(this)

    @JvmStatic
    internal fun domainInternal(
        contractTransaction: ProtoContractTransaction
    ): ContractTransaction = contractTransaction.run {
        when (dataCase) {
            CREATE_DATA -> CreateContractTransaction(
                id = TxId.fromBase58(id),
                type = TxType.fromInt(type),
                sender = Address.fromBase58(sender),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                contractId = ContractId.fromBase58(contractId),
                params = paramsList.map { it.domain() },
                fee = Fee.fromLong(fee),
                version = TxVersion.fromInt(version),
                proof = Signature(proofs.toByteArray()),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                feeAssetId = AssetId.fromBase58(feeAssetId.value),
                image = ContractImage.fromString(createData.image),
                imageHash = ContractImageHash.fromString(createData.imageHash),
                contractName = ContractName.fromString(createData.contractName)
            )
            DataCase.CALL_DATA -> CallContractTransaction(
                id = TxId.fromBase58(id),
                type = TxType.fromInt(type),
                sender = Address.fromBase58(sender),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                contractId = ContractId.fromBase58(contractId),
                params = paramsList.map { it.domain() },
                fee = Fee.fromLong(fee),
                version = TxVersion.fromInt(version),
                proof = Signature(proofs.toByteArray()),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                feeAssetId = AssetId.fromBase58(feeAssetId.value),
                contractVersion = ContractVersion.fromInt(callData.contractVersion)
            )
            null, DataCase.DATA_NOT_SET ->
                throw IllegalStateException("ContractTransaction.dataCase shouldn't be null or unset")
        }
    }
}
