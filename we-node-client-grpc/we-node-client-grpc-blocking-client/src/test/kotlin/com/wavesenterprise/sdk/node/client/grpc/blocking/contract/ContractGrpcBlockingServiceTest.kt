package com.wavesenterprise.sdk.node.client.grpc.blocking.contract

import com.wavesenterprise.protobuf.service.contract.ContractKeyResponse
import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc
import com.wavesenterprise.sdk.node.domain.DataValue
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeyRequest
import com.wavesenterprise.sdk.node.exception.NodeException
import com.wavesenterprise.sdk.node.exception.specific.DataKeyNotExistException
import io.grpc.Metadata
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ContractGrpcBlockingServiceTest {

    @MockK
    lateinit var protoContractService: ContractServiceGrpc.ContractServiceBlockingStub

    @Test
    fun `should throw ContractNotFoundException for not found`() {
        val statusRuntimeExceptionNullMetadata = StatusRuntimeException(Status.NOT_FOUND)
        every { protoContractService.getContractKey(any()) } throws statusRuntimeExceptionNullMetadata
        val contractGrpcBlockingService = ContractGrpcBlockingService(
            mockk(),
            mockk(),
            protoContractService
        )

        assertThrows<DataKeyNotExistException> {
            contractGrpcBlockingService.getContractKey(
                ContractKeyRequest(
                    contractId = ContractId.fromBase58("2nfSLahtZMk8wjD5fiPtfYiNYDKmyNpgSvB8bRgPSrQU"),
                    key = "notFoundKey"
                )
            )
        }
    }

    @Test
    fun `should propagate exception for other errors`() {
        val internalErrorStatus = Status.INTERNAL
        val metadata: Metadata = mockedMetadata(internalErrorStatus)
        every { metadata.get(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER)) } returns null
        every { metadata.get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER)) } returns null
        val statusRuntimeException = StatusRuntimeException(internalErrorStatus, metadata)
        every { protoContractService.getContractKey(any()) } throws statusRuntimeException
        val contractGrpcBlockingService = ContractGrpcBlockingService(
            mockk(),
            mockk(),
            protoContractService
        )
        assertThrows<NodeException> {
            contractGrpcBlockingService.getContractKey(
                ContractKeyRequest(
                    contractId = ContractId.fromBase58("2nfSLahtZMk8wjD5fiPtfYiNYDKmyNpgSvB8bRgPSrQU"),
                    key = "notFoundKey"
                )
            )
        }
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
        ).get()

        assertNotNull(contractKeyResponse)
        contractKeyResponse.also {
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
