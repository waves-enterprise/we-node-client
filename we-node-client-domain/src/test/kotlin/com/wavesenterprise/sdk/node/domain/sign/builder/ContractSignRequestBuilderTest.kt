package com.wavesenterprise.sdk.node.domain.sign.builder

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.DataValue
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractName
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ContractSignRequestBuilderTest {

    lateinit var contractSignRequestBuilder: ContractSignRequestBuilder

    @BeforeEach
    fun init() {
        contractSignRequestBuilder = ContractSignRequestBuilder()
    }

    @Test
    fun `should build create contract sign request`() {
        contractSignRequestBuilder
            .senderAddress(Address(TxId.fromBase58("d").bytes))
            .fee(Fee(0L))
            .image(ContractImage(""))
            .imageHash(Hash(ByteArray(1)))
            .contractName(ContractName(""))
            .params(listOf(DataEntry(DataKey(""), DataValue.StringDataValue(""))))
            .build(txType = TxType.CREATE_CONTRACT)
    }

    @Test
    fun `should build call contract sign request`() {
        contractSignRequestBuilder
            .senderAddress(Address(TxId.fromBase58("d").bytes))
            .fee(Fee(0L))
            .contractId(ContractId.fromBase58(""))
            .contractVersion(ContractVersion(0))
            .params(listOf(DataEntry(DataKey(""), DataValue.StringDataValue(""))))
            .build(txType = TxType.CALL_CONTRACT)
    }

    @Test
    fun `should throw IllegalArgumentException when set empty params for sign request`() {
        assertThrows<IllegalArgumentException> { contractSignRequestBuilder.params(emptyList()) }.apply {
            assertEquals("params can't be empty", message)
        }
    }

    @Test
    fun `should throw IllegalArgumentException when not nullable fields is null for create contract sign request`() {
        assertThrows<IllegalStateException> {
            contractSignRequestBuilder
                .senderAddress(Address(TxId.fromBase58("d").bytes))
                .build(txType = TxType.CREATE_CONTRACT)
        }.apply {
            assertEquals(
                "Fields: [fee, image, imageHash, contractName, params] can not be null - for CreateContractSignRequest",
                message,
            )
        }
    }

    @Test
    fun `should throw IllegalArgumentException when not nullable fields is null for call contract sign request`() {
        assertThrows<IllegalStateException> {
            contractSignRequestBuilder
                .senderAddress(Address(TxId.fromBase58("d").bytes))
                .build(txType = TxType.CALL_CONTRACT)
        }.apply {
            assertEquals(
                "Fields: [fee, params, contractId, contractVersion] can not be null - for CallContractSignRequest",
                message,
            )
        }
    }
}
