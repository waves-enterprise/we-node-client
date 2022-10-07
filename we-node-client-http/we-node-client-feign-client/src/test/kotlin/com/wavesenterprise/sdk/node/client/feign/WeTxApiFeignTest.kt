package com.wavesenterprise.sdk.node.client.feign

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.tx.WeTxApiFeign
import com.wavesenterprise.sdk.node.domain.http.DataEntryDto
import com.wavesenterprise.sdk.node.domain.http.sign.CallContractSignRequestDto
import com.wavesenterprise.sdk.node.domain.http.sign.CreateContractSignRequestDto
import com.wavesenterprise.sdk.node.domain.http.tx.CallContractTxDto
import com.wavesenterprise.sdk.node.domain.http.tx.CreateContractTxDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@WireMockTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WeTxApiFeignTest {

    lateinit var weTxApi: WeTxApiFeign

    @BeforeAll
    fun init(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        weTxApi = FeignWeApiFactory.createClient(
            WeTxApiFeign::class.java,
            FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl)
        )
    }

    @Test
    fun `should get utxInfo`() {
        val utxInfo = weTxApi.utxInfo()

        assertEquals(10, utxInfo.size)
        assertEquals(250, utxInfo.sizeInBytes)
    }

    @Test
    fun `should successfully sign SignRequest`() {
        val signedTxResponse: CallContractTxDto = weTxApi.sign(
            CallContractSignRequestDto(
                contractId = "8mYpzj5rVVQSp2DgsyMvoMSViHtX94dJHkPyX2xo855y",
                fee = 0L,
                params = listOf(
                    DataEntryDto(
                        key = "action", type = "string", value = "registerPerson"
                    ),
                    DataEntryDto(
                        key = "arg", type = "string", value = "{\"snils\": \"12345\",\"name\": \"Vasya\",\"age\": 35}"
                    )
                ),
                version = 2,
                contractVersion = 1,
                sender = "3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX",
                password = null,
                feeAssetId = null,
                atomicBadge = null
            )
        )

        assertEquals("Gv5RFssBGVZJRDoN5i9s8w6EkMKTzUCiJ5zxeAuXhb8c", signedTxResponse.id)
    }

    @Test
    fun `should successfully signAndBroadcast SignRequest`() {
        val createContractTxDto: CreateContractTxDto = weTxApi.signAndBroadcast(
            CreateContractSignRequestDto(
                contractName = "java-contract",
                fee = 0L,
                params = listOf(
                    DataEntryDto(
                        key = "action",
                        type = "string",
                        value = "createContractWithInitialValue"
                    ),
                    DataEntryDto(
                        key = "createContract",
                        type = "string",
                        value = "ID_1"
                    )
                ),
                version = 2,
                sender = "3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX",
                password = null,
                feeAssetId = null,
                atomicBadge = null,
                apiVersion = null,
                validationPolicy = null,
                imageHash = "586d70cb288e82198a924871d75849c263c51b205764fa3e51755e54fcde18e8",
                image = "registry.weintegrator.com/icore-sc/we-contract-sdk/samples/demo-example:1.0.1"
            )
        )

        assertEquals("5inUANAmDzRfq5f1Yv7HBTm8G4AREPfeKCntaEDDqVbU", createContractTxDto.id)
    }

    @Test
    fun `should get TxInfo for found tx info`() {
        val txId = "8mYpzj5rVVQSp2DgsyMvoMSViHtX94dJHkPyX2xo855y"
        val foundTxInfo = weTxApi.txInfo(txId)

        assertTrue(foundTxInfo.isPresent)
        foundTxInfo.get().apply {
            assertEquals(txId, id)
            assertEquals(5577377, height)
        }
    }

    @Test
    fun `should get Optional empty for not found tx info`() {
        val notFoundTxInfo = weTxApi.txInfo("df")

        assertFalse(notFoundTxInfo.isPresent)
    }
}
