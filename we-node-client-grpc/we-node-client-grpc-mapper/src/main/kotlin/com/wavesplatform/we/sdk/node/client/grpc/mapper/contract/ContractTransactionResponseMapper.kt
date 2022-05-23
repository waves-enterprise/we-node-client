package com.wavesplatform.we.sdk.node.client.grpc.mapper.contract

import com.wavesenterprise.protobuf.service.contract.ContractTransaction.DataCase
import com.wavesenterprise.protobuf.service.contract.ContractTransaction.DataCase.CREATE_DATA
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.contract.CallContractTransaction
import com.wavesplatform.we.sdk.node.client.contract.ContractId
import com.wavesplatform.we.sdk.node.client.contract.ContractImage
import com.wavesplatform.we.sdk.node.client.contract.ContractImageHash
import com.wavesplatform.we.sdk.node.client.contract.ContractName
import com.wavesplatform.we.sdk.node.client.contract.ContractTransaction
import com.wavesplatform.we.sdk.node.client.contract.ContractTransactionResponse
import com.wavesplatform.we.sdk.node.client.contract.ContractVersion
import com.wavesplatform.we.sdk.node.client.contract.CreateContractTransaction
import com.wavesplatform.we.sdk.node.client.grpc.mapper.DataEntryMapper.domain
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
