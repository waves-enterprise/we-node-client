package com.wavesenterprise.sdk.node.client.feign

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wavesenterprise.sdk.node.exception.NodeBadRequestException
import com.wavesenterprise.sdk.node.exception.NodeConflictException
import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.NodeNotFoundException
import com.wavesenterprise.sdk.node.exception.specific.ContractNotFoundException
import com.wavesenterprise.sdk.node.exception.specific.CustomValidationErrorException
import com.wavesenterprise.sdk.node.exception.specific.DataKeyNotExistsException
import com.wavesenterprise.sdk.node.exception.specific.InvalidAddressException
import com.wavesenterprise.sdk.node.exception.specific.InvalidSignatureException
import com.wavesenterprise.sdk.node.exception.specific.PolicyDoesNotExistException
import com.wavesenterprise.sdk.node.exception.specific.PolicyItemDataIsMissingException
import com.wavesenterprise.sdk.node.exception.specific.PrivacyApiKeyNotValidException
import feign.FeignException
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FeignNodeErrorMapperTest {

    private lateinit var feignNodeErrorMapper: FeignNodeErrorMapper

    @BeforeEach
    fun `init`() {
        feignNodeErrorMapper = FeignNodeErrorMapper(jacksonObjectMapper())
    }

    @Test
    fun `should map FeignException to InvalidSignatureException`() {
        val nodeError = NodeError(error = 101, message = "invalid signature")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.NotFound(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is InvalidSignatureException)
            (this as InvalidSignatureException).apply {
                assertEquals(this.nodeError.error, nodeError.error)
                assertEquals(this.nodeError.message, nodeError.message)
            }
        }
    }

    @Test
    fun `should map FeignException to InvalidAddressException`() {
        val nodeError = NodeError(error = 102, message = "invalid address")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.BadRequest(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is InvalidAddressException)
            (this as InvalidAddressException).apply {
                assertEquals(this.nodeError.error, nodeError.error)
                assertEquals(this.nodeError.message, nodeError.message)
            }
        }
    }

    @Test
    fun `should map FeignException to CustomValidationErrorException`() {
        val nodeError = NodeError(error = 199, message = "Failed to decode input data: expecting Base64 encoded data")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.BadRequest(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is CustomValidationErrorException)
            (this as CustomValidationErrorException).apply {
                assertEquals(this.nodeError.error, nodeError.error)
                assertEquals(this.nodeError.message, nodeError.message)
            }
        }
    }

    @Test
    fun `should map FeignException to DataKeyNotExistsException`() {
        val nodeError = NodeError(error = 304, message = "no data for this key")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.NotFound(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is DataKeyNotExistsException)
            (this as DataKeyNotExistsException).apply {
                assertEquals(this.nodeError.error, nodeError.error)
                assertEquals(this.nodeError.message, nodeError.message)
            }
        }
    }

    @Test
    fun `should map FeignException to ContractNotFoundException`() {
        val nodeError = NodeError(error = 600, message = "Contract is not found")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.NotFound(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is ContractNotFoundException)
            (this as ContractNotFoundException).apply {
                assertEquals(this.nodeError.error, nodeError.error)
                assertEquals(this.nodeError.message, nodeError.message)
            }
        }
    }

    @Test
    fun `should map FeignException to PolicyDoesNotExistException`() {
        val nodeError = NodeError(error = 612, message = "The requested policy does not exist")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.NotFound(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is PolicyDoesNotExistException)
            (this as PolicyDoesNotExistException).apply {
                assertEquals(this.nodeError.error, nodeError.error)
                assertEquals(this.nodeError.message, nodeError.message)
            }
        }
    }

    @Test
    fun `should map FeignException to PrivacyApiKeyNotValidException`() {
        val nodeError = NodeError(error = 614, message = "Provided privacy API key is not correct")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.BadRequest(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is PrivacyApiKeyNotValidException)
            (this as PrivacyApiKeyNotValidException).apply {
                assertEquals(this.nodeError.error, nodeError.error)
                assertEquals(this.nodeError.message, nodeError.message)
            }
        }
    }

    @Test
    fun `should map FeignException to PolicyItemDataIsMissingException`() {
        val nodeError = NodeError(error = 617, message = "The requested dataset is missing in privacy storage")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.NotFound(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is PolicyItemDataIsMissingException)
            (this as PolicyItemDataIsMissingException).apply {
                assertEquals(this.nodeError.error, nodeError.error)
                assertEquals(this.nodeError.message, nodeError.message)
            }
        }
    }

    @Test
    fun `should map FeignException to NotFound`() {
        val nodeError = NodeError(error = 999, message = "Unknown")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.NotFound(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is NodeNotFoundException)
            (this as NodeNotFoundException).apply {
                assertNotNull(this.nodeError)
                assertEquals(this.nodeError!!.error, nodeError.error)
                assertEquals(this.nodeError!!.message, nodeError.message)
            }
        }
    }

    @Test
    fun `should map FeignException to BadRequest`() {
        val nodeError = NodeError(error = 999, message = "Unknown")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.BadRequest(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is NodeBadRequestException)
            (this as NodeBadRequestException).apply {
                assertNotNull(this.nodeError)
                assertEquals(this.nodeError!!.error, nodeError.error)
                assertEquals(this.nodeError!!.message, nodeError.message)
            }
        }
    }

    @Test
    fun `should map FeignException to Conflict`() {
        val nodeError = NodeError(error = 999, message = "Unknown")
        val ex = feignNodeErrorMapper.mapToGeneralException(
            FeignException.Conflict(
                "", mockk(),
                """
                    ${jacksonObjectMapper().writeValueAsString(nodeError)}
                """.trimIndent().toByteArray(),
                null
            )
        )
        ex.apply {
            assertTrue(this is NodeConflictException)
            (this as NodeConflictException).apply {
                assertNotNull(this.nodeError)
                assertEquals(this.nodeError!!.error, nodeError.error)
                assertEquals(this.nodeError!!.message, nodeError.message)
            }
        }
    }
}
