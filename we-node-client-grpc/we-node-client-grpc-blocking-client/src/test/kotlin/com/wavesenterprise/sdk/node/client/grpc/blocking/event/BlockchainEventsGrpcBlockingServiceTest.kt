package com.wavesenterprise.sdk.node.client.grpc.blocking.event

import com.wavesenterprise.protobuf.service.messagebroker.BlockchainEventsServiceGrpc
import com.wavesenterprise.protobuf.service.messagebroker.MessagebrokerBlockchainEvent
import com.wavesenterprise.protobuf.service.messagebroker.MessagebrokerBlockchainEvent.BlockAppended
import com.wavesenterprise.protobuf.service.messagebroker.MessagebrokerBlockchainEvent.BlockchainEvent
import com.wavesenterprise.protobuf.service.messagebroker.MessagebrokerBlockchainEvent.MicroBlockAppended
import com.wavesenterprise.protobuf.service.util.events.UtilEventsSubscribeOnRequest
import com.wavesenterprise.sdk.node.client.grpc.mapper.event.BlockchainEventMapper.domain
import com.wavesenterprise.sdk.node.domain.event.StartFrom
import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.MethodDescriptor
import io.grpc.stub.ClientCalls
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
class BlockchainEventsGrpcBlockingServiceTest {

    @MockK
    lateinit var blockchainEventsServiceBlockingStub: BlockchainEventsServiceGrpc.BlockchainEventsServiceBlockingStub

    @MockK
    lateinit var channel: Channel

    @MockK(relaxed = true)
    lateinit var clientCall: ClientCall<SubscribeOnRequest, BlockchainEvent>

    @BeforeEach
    fun setUp() {
        every {
            channel.newCall(any<MethodDescriptor<SubscribeOnRequest, BlockchainEvent>>(), any())
        } returns clientCall
        every {
            blockchainEventsServiceBlockingStub.channel
        } returns channel
    }

    @ParameterizedTest
    @MethodSource("blockchainEvents")
    fun `should return event`(
        expectedEvent: BlockchainEvent,
    ) {
        val expectedBlockchainEventIterator = listOf(expectedEvent).iterator()
        mockkStatic(ClientCalls::class)
        every {
            ClientCalls.blockingServerStreamingCall(
                any<ClientCall<UtilEventsSubscribeOnRequest.SubscribeOnRequest, BlockchainEvent>>(), any(),
            )
        } returns expectedBlockchainEventIterator

        val blockchainEventsGrpcBlockingService = BlockchainEventsGrpcBlockingService(
            channel = channel,
            grpc = blockchainEventsServiceBlockingStub,
        )
        val subscribeOnRequest = SubscribeOnRequest(
            startFrom = StartFrom.Current,
            filters = listOf(),
        )
        val blockchainEventsIterator = blockchainEventsGrpcBlockingService.events(request = subscribeOnRequest)
        assertEquals(blockchainEventsIterator.next(), expectedEvent.domain())
    }

    companion object {

        @JvmStatic
        private fun blockchainEvents(): Stream<Arguments> =
            setOf(
                BlockchainEvent.newBuilder()
                    .setMicroBlockAppended(MicroBlockAppended.newBuilder().build())
                    .build(),
                BlockchainEvent.newBuilder()
                    .setBlockAppended(BlockAppended.newBuilder().build())
                    .build(),
                BlockchainEvent.newBuilder()
                    .setRollbackCompleted(MessagebrokerBlockchainEvent.RollbackCompleted.newBuilder().build())
                    .build(),
                BlockchainEvent.newBuilder()
                    .setAppendedBlockHistory(MessagebrokerBlockchainEvent.AppendedBlockHistory.newBuilder().build())
                    .build(),
            ).map { Arguments.of(it) }.stream()
    }
}
