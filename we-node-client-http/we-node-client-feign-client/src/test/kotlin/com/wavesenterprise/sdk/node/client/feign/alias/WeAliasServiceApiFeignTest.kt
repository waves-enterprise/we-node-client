package com.wavesenterprise.sdk.node.client.feign.alias

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.FeignNodeClientParams
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorDecoder
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorMapper
import com.wavesenterprise.sdk.node.client.feign.FeignWeApiFactory
import com.wavesenterprise.sdk.node.client.http.AddressDto
import com.wavesenterprise.sdk.node.exception.NodeErrorCode
import com.wavesenterprise.sdk.node.exception.specific.AliasNotExistException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.util.Optional

@WireMockTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeAliasServiceApiFeignTest {
    private lateinit var weAliasServiceApiFeign: WeAliasServiceApiFeign

    @BeforeAll
    fun init(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        weAliasServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WeAliasServiceApiFeign::class.java,
            feignProperties = FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl),
            errorDecoder = FeignNodeErrorDecoder(FeignNodeErrorMapper(jacksonObjectMapper())),
        )
    }

    @Test
    fun `should get aliases by address`() {
        val address = "3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX"
        assertEquals(
            listOf(
                "alias:R:_firstAlias",
                "alias:R:_secondAlias",
            ),
            weAliasServiceApiFeign.getAliasesByAddress(address),
        )
    }

    @Test
    fun `should get address by alias`() {
        val alias = "_firstAlias"
        val address = "3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX"
        assertEquals(
            Optional.of(AddressDto(address)),
            weAliasServiceApiFeign.getAddressByAlias(alias)
        )
    }

    @Test
    fun `should throw AliasNotExistException`() {
        assertThrows<AliasNotExistException> {
            weAliasServiceApiFeign.getAddressByAlias("_non-existent-alias")
        }.apply {
            assertEquals(NodeErrorCode.ALIAS_NOT_EXIST.code, nodeError.error)
            assertEquals(
                "alias 'alias:R:_non-existent-alias' doesn't exist",
                nodeError.message
            )
        }
    }
}
