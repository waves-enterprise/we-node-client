package com.wavesenterprise.sdk.node.client.grpc.blocking

import com.wavesenterprise.sdk.node.client.grpc.blocking.mapper.GrpcNodeErrorMapper
import com.wavesenterprise.sdk.node.exception.NodeBadRequestException
import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.NodeException
import com.wavesenterprise.sdk.node.exception.specific.ContractNotFoundException
import com.wavesenterprise.sdk.node.exception.specific.CustomValidationErrorException
import com.wavesenterprise.sdk.node.exception.specific.DataKeyNotExistsException
import com.wavesenterprise.sdk.node.exception.specific.InvalidAddressException
import com.wavesenterprise.sdk.node.exception.specific.InvalidSignatureException
import com.wavesenterprise.sdk.node.exception.specific.PolicyDoesNotExistException
import com.wavesenterprise.sdk.node.exception.specific.PolicyItemDataIsMissingException
import com.wavesenterprise.sdk.node.exception.specific.PrivacyApiKeyNotValidException
import io.grpc.Metadata
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class GrpcNodeErrorMapperTest {

    @Test
    fun `should map StatusRuntimeException to InvalidSignatureException`() {
        val expectedNodeError = NodeError(error = 101, message = "invalid signature")
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.error.toString()
        every {
            metadata.get(
                Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER)
            )
        } returns expectedNodeError.message
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        ex.apply {
            assertTrue(this is InvalidSignatureException).let {
                (this as InvalidSignatureException).apply {
                    assertEquals(expectedNodeError.error, nodeError.error)
                    assertEquals(expectedNodeError.message, nodeError.message)
                }
            }
        }
    }

    @Test
    fun `should map StatusRuntimeException to InvalidAddressException`() {
        val expectedNodeError = NodeError(error = 102, message = "invalid address")
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.error.toString()
        every {
            metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.message
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        ex.apply {
            assertTrue(this is InvalidAddressException).let {
                (this as InvalidAddressException).apply {
                    assertEquals(expectedNodeError.error, nodeError.error)
                    assertEquals(expectedNodeError.message, nodeError.message)
                }
            }
        }
    }

    @Test
    fun `should map StatusRuntimeException to CustomValidationErrorException`() {
        val expectedNodeError = NodeError(error = 199, message = "Failed to decode input data: expecting Base64 encoded data")
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.error.toString()
        every {
            metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.message
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        ex.apply {
            assertTrue(this is CustomValidationErrorException).let {
                (this as CustomValidationErrorException).apply {
                    assertEquals(expectedNodeError.error, nodeError.error)
                    assertEquals(expectedNodeError.message, nodeError.message)
                }
            }
        }
    }

    @Test
    fun `should map StatusRuntimeException to DataKeyNotExistsException`() {
        val expectedNodeError = NodeError(error = 304, message = "no data for this key")
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.error.toString()
        every {
            metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.message
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        ex.apply {
            assertTrue(this is DataKeyNotExistsException).let {
                (this as DataKeyNotExistsException).apply {
                    assertEquals(expectedNodeError.error, nodeError.error)
                    assertEquals(expectedNodeError.message, nodeError.message)
                }
            }
        }
    }

    @Test
    fun `should map StatusRuntimeException to ContractNotFoundException`() {
        val expectedNodeError = NodeError(error = 600, message = "Contract is not found")
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.error.toString()
        every {
            metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.message
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        ex.apply {
            assertTrue(this is ContractNotFoundException).let {
                (this as ContractNotFoundException).apply {
                    assertEquals(expectedNodeError.error, nodeError.error)
                    assertEquals(expectedNodeError.message, nodeError.message)
                }
            }
        }
    }

    @Test
    fun `should map StatusRuntimeException to PolicyDoesNotExistException`() {
        val expectedNodeError = NodeError(error = 612, message = "The requested policy does not exist")
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.error.toString()
        every {
            metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.message
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        ex.apply {
            assertTrue(this is PolicyDoesNotExistException).let {
                (this as PolicyDoesNotExistException).apply {
                    assertEquals(expectedNodeError.error, nodeError.error)
                    assertEquals(expectedNodeError.message, nodeError.message)
                }
            }
        }
    }

    @Test
    fun `should map StatusRuntimeException to PrivacyApiKeyNotValidException`() {
        val expectedNodeError = NodeError(error = 614, message = "Provided privacy API key is not correct")
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.error.toString()
        every {
            metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.message
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        ex.apply {
            assertTrue(this is PrivacyApiKeyNotValidException).let {
                (this as PrivacyApiKeyNotValidException).apply {
                    assertEquals(expectedNodeError.error, nodeError.error)
                    assertEquals(expectedNodeError.message, nodeError.message)
                }
            }
        }
    }

    @Test
    fun `should map StatusRuntimeException to PolicyItemDataIsMissingException`() {
        val expectedNodeError = NodeError(error = 617, message = "The requested dataset is missing in privacy storage")
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.error.toString()
        every {
            metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.message
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        ex.apply {
            assertTrue(this is PolicyItemDataIsMissingException).let {
                (this as PolicyItemDataIsMissingException).apply {
                    assertEquals(expectedNodeError.error, nodeError.error)
                    assertEquals(expectedNodeError.message, nodeError.message)
                }
            }
        }
    }

    @Test
    fun `should map StatusRuntimeException to default NodeException`() {
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.OK, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns null
        every {
            metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER))
        } returns null
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        assertTrue(ex is NodeException)
    }

    @Test
    fun `should map StatusRuntimeException to not specific exception without node error info`() {
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns null
        every {
            metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER))
        } returns null
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        ex.apply {
            assertTrue(this is NodeBadRequestException).let {
                (this as NodeBadRequestException).apply {
                    assertNull(this.nodeError)
                }
            }
        }
    }

    @Test
    fun `should map StatusRuntimeException to not specific exception with node error info`() {
        val expectedNodeError = NodeError(error = 999, message = "new error")
        val metadata: Metadata = mockk()
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT, metadata)
        every {
            metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.error.toString()
        every {
            metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER))
        } returns expectedNodeError.message
        val ex = GrpcNodeErrorMapper.mapToGeneralException(statusRuntimeException)
        ex.apply {
            assertTrue(this is NodeBadRequestException).let {
                (this as NodeBadRequestException).apply {
                    assertNotNull(this.nodeError)
                    assertEquals(expectedNodeError.error, nodeError!!.error)
                    assertEquals(expectedNodeError.message, nodeError!!.message)
                }
            }
        }
    }
}
