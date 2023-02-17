package com.wavesenterprise.sdk.node.test.data

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.Password
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
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import com.wavesenterprise.sdk.node.domain.contract.CreateContractTransaction
import com.wavesenterprise.sdk.node.domain.sign.CreateContractSignRequest
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.sdk.node.test.data.Util.Companion.randomBytesFromUUID
import java.time.Instant
import java.util.UUID

class TestDataFactory private constructor() {

    companion object {
        @JvmStatic
        fun address() = Address(randomBytesFromUUID())

        @JvmStatic
        fun txId() = TxId(randomBytesFromUUID())

        @JvmStatic
        fun password() = Password("password")

        @JvmStatic
        fun contractTransaction(
            sender: Address,
            type: TxType = TxType.CALL_CONTRACT,
        ): ContractTransaction =
            when (type) {
                TxType.CALL_CONTRACT -> CallContractTransaction(
                    id = txId(),
                    sender = sender,
                    contractId = ContractId(txId()),
                    params = emptyList(),
                    fee = Fee(0),
                    type = type,
                    version = TxVersion.fromInt(0),
                    proof = Signature(randomBytesFromUUID()),
                    timestamp = Timestamp.fromUtcTimestamp(Instant.now().toEpochMilli()),
                    contractVersion = ContractVersion(0),
                    feeAssetId = AssetId(randomBytesFromUUID()),
                    senderPublicKey = PublicKey(randomBytesFromUUID())
                )
                TxType.CREATE_CONTRACT -> CreateContractTransaction(
                    id = txId(),
                    sender = sender,
                    contractId = ContractId(txId()),
                    params = emptyList(),
                    fee = Fee(0),
                    type = type,
                    version = TxVersion.fromInt(0),
                    proof = Signature(randomBytesFromUUID()),
                    timestamp = Timestamp.fromUtcTimestamp(Instant.now().toEpochMilli()),
                    feeAssetId = AssetId(randomBytesFromUUID()),
                    senderPublicKey = PublicKey(randomBytesFromUUID()),
                    contractName = ContractName("test-contract-${UUID.randomUUID()}"),
                    image = ContractImage("image"),
                    imageHash = ContractImageHash("imageHash"),
                )
                else -> throw IllegalArgumentException(
                    "Only CALL_CONTRACT and CREATE_CONTRACT are allowed as transaction type"
                )
            }

        @JvmStatic
        fun callContractTx(
            id: TxId = txId(),
            params: List<DataEntry> = emptyList(),
        ) = CallContractTx(
            id = id,
            senderPublicKey = PublicKey(ByteArray(1)),
            contractId = ContractId(TxId(ByteArray(1))),
            params = params,
            fee = Fee(1L),
            version = TxVersion(1),
            proofs = null,
            timestamp = Timestamp(1L),
            feeAssetId = null,
            contractVersion = ContractVersion(1),
            atomicBadge = null,
            senderAddress = Address(ByteArray(1)),
        )

        @JvmStatic
        fun createContractTx(
            id: TxId = txId(),
            params: List<DataEntry> = emptyList(),
        ) = CreateContractTx(
            id = id,
            senderPublicKey = PublicKey(ByteArray(1)),
            params = params,
            fee = Fee(1L),
            version = TxVersion(1),
            proofs = null,
            timestamp = Timestamp(1L),
            feeAssetId = null,
            image = ContractImage("ContractImage"),
            imageHash = ContractImageHash("ContractImageHash"),
            contractName = ContractName("ContractName"),
            atomicBadge = null,
            senderAddress = Address(ByteArray(1)),
        )

        @JvmStatic
        fun createContractSignRequest(
            senderAddress: Address = Address.fromBase58("3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX"),
        ) = CreateContractSignRequest(
            version = TxVersion(2),

            // sender specific
            senderAddress = senderAddress,
            password = Password("bla"), // only for signature by node
            // ---------------

            // fee specific
            fee = Fee(0),
            feeAssetId = FeeAssetId.fromBase58("GQNK5zxqt1a6cYxnYfG4Svk6HvEjwBdeq61vP2FnNSSp"),
            // ---------------

            // contract specific
            contractName = ContractName("bla"),
            image = ContractImage("image"),
            imageHash = ContractImageHash("ContractImageHash"),
            params = listOf(),
            // -----------------
        )
    }
}
