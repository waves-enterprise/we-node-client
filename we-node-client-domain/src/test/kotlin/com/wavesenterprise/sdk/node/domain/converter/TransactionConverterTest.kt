package com.wavesenterprise.sdk.node.domain.converter

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.DataValue
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractName
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TransactionConverterTest {

    @Test
    fun `should convert to CreateContractTransaction`() {
        createContractTx().toContractTransaction().apply {
            assertEquals(id, this.id)
            assertEquals(TxType.CREATE_CONTRACT, this.type)
            assertEquals(params, this.params)
        }
    }

    @Test
    fun `should convert to CallContractTransaction`() {
        callContractTx().toContractTransaction().apply {
            assertEquals(id, this.id)
            assertEquals(TxType.CALL_CONTRACT, this.type)
            assertEquals(params, this.params)
        }
    }

    companion object {
        private val id = TxId(ByteArray(1))
        private val params = listOf(
            DataEntry(
                key = DataKey("key"),
                value = DataValue.StringDataValue("value")
            )
        )

        fun callContractTx() = CallContractTx(
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

        fun createContractTx() = CreateContractTx(
            id = id,
            senderPublicKey = PublicKey(ByteArray(1)),
            params = params,
            fee = Fee(1L),
            version = TxVersion(1),
            proofs = null,
            timestamp = Timestamp(1L),
            feeAssetId = null,
            image = ContractImage("ContractImage"),
            imageHash = Hash(ByteArray(1)),
            contractName = ContractName("ContractName"),
            atomicBadge = null,
            senderAddress = Address(ByteArray(1)),
        )
    }
}
