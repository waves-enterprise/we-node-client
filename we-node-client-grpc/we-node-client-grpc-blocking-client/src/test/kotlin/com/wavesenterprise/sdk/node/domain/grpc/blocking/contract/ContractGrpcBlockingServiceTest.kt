package com.wavesenterprise.sdk.node.domain.grpc.blocking.contract

import com.wavesenterprise.protobuf.service.contract.ContractKeyResponse
import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc
import com.wavesenterprise.sdk.node.domain.DataValue
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeyRequest
import io.grpc.Metadata
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ContractGrpcBlockingServiceTest {

    @MockK
    lateinit var protoContractService: ContractServiceGrpc.ContractServiceBlockingStub

    @Test
    fun `should return null for not found key`() {
        val status = Status.NOT_FOUND
        val metadata: Metadata = mockedMetadata(status)
        every { protoContractService.getContractKey(any()) } throws
            StatusRuntimeException(status, metadata)
        val contractGrpcBlockingService = ContractGrpcBlockingService(
            mockk(),
            mockk(),
            protoContractService
        )

        assertNull(
            contractGrpcBlockingService.getContractKey(
                ContractKeyRequest(
                    contractId = ContractId.fromBase58("2nfSLahtZMk8wjD5fiPtfYiNYDKmyNpgSvB8bRgPSrQU"),
                    key = "notFoundKey"
                )
            )
        )
    }

    @Test
    fun `should return null for not found with null metadata`() {
        val statusRuntimeExceptionNullMetadata = StatusRuntimeException(Status.NOT_FOUND)
        every { protoContractService.getContractKey(any()) } throws statusRuntimeExceptionNullMetadata
        val contractGrpcBlockingService = ContractGrpcBlockingService(
            mockk(),
            mockk(),
            protoContractService
        )

        assertNull(
            contractGrpcBlockingService.getContractKey(
                ContractKeyRequest(
                    contractId = ContractId.fromBase58("2nfSLahtZMk8wjD5fiPtfYiNYDKmyNpgSvB8bRgPSrQU"),
                    key = "notFoundKey"
                )
            )
        )
    }

    @Test
    fun `should propagate exception for other errors`() {
        val internalErrorStatus = Status.INTERNAL
        val metadata: Metadata = mockedMetadata(internalErrorStatus)
        val statusRuntimeException = StatusRuntimeException(internalErrorStatus, metadata)
        every { protoContractService.getContractKey(any()) } throws statusRuntimeException
        val contractGrpcBlockingService = ContractGrpcBlockingService(
            mockk(),
            mockk(),
            protoContractService
        )

        val actualException = assertThrows<StatusRuntimeException> {
            contractGrpcBlockingService.getContractKey(
                ContractKeyRequest(
                    contractId = ContractId.fromBase58("2nfSLahtZMk8wjD5fiPtfYiNYDKmyNpgSvB8bRgPSrQU"),
                    key = "notFoundKey"
                )
            )
        }

        assertEquals(statusRuntimeException, actualException)
    }

    @Test
    fun `should return key value when ok`() {
        val key = "key"
        val value = "value"
        every { protoContractService.getContractKey(any()) } returns ContractKeyResponse.newBuilder().apply {
            entry = entryBuilder.setKey(key).setStringValue(value).build()
        }.build()
        val contractGrpcBlockingService = ContractGrpcBlockingService(
            mockk(),
            mockk(),
            protoContractService
        )

        val contractKeyResponse = contractGrpcBlockingService.getContractKey(
            ContractKeyRequest(
                contractId = ContractId.fromBase58("2nfSLahtZMk8wjD5fiPtfYiNYDKmyNpgSvB8bRgPSrQU"),
                key = "notFoundKey"
            )
        )

        assertNotNull(contractKeyResponse)
        contractKeyResponse!!.also {
            assertEquals(key, it.key.value)
            assertEquals(value, (it.value as DataValue.StringDataValue).value)
        }
    }

    private fun mockedMetadata(status: Status): Metadata {
        val grpcStatus = com.google.rpc.Status.newBuilder().setCode(status.code.value()).build()
        val metadata: Metadata = mockk()
        every { metadata.get<com.google.rpc.Status>(any()) } returns grpcStatus
        return metadata
    }
}
