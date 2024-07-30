package com.wavesenterprise.sdk.node.client.grpc.blocking.event

import com.wavesenterprise.protobuf.service.messagebroker.BlockchainEventsServiceGrpc
import com.wavesenterprise.protobuf.service.messagebroker.BlockchainEventsServiceGrpc.BlockchainEventsServiceBlockingStub
import com.wavesenterprise.protobuf.service.messagebroker.MessagebrokerBlockchainEvent.BlockchainEvent
import com.wavesenterprise.protobuf.service.util.events.UtilEventsSubscribeOnRequest
import com.wavesenterprise.protobuf.service.util.events.UtilEventsSubscribeOnRequest.CurrentEvent
import com.wavesenterprise.protobuf.service.util.events.UtilEventsSubscribeOnRequest.GenesisBlock
import com.wavesenterprise.protobuf.service.util.events.UtilEventsSubscribeOnRequest.SubscribeOnRequest
import com.wavesenterprise.sdk.node.client.blocking.event.BlockchainEventsIterator
import com.wavesenterprise.sdk.node.client.blocking.event.BlockchainEventsService
import com.wavesenterprise.sdk.node.client.grpc.blocking.mapper.map
import com.wavesenterprise.sdk.node.client.grpc.blocking.util.catchingNodeCall
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.event.BlockchainEventMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.event.EventFilterMapper.dto
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.event.StartFrom
import com.wavesenterprise.sdk.node.exception.EventReceivingException
import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.stub.ClientCalls
import com.wavesenterprise.sdk.node.domain.event.BlockchainEvent as WeBlockchainEvent
import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest as WeSubscribeOnRequest

private typealias ProtoEventsIterator = Iterator<BlockchainEvent>

class BlockchainEventsGrpcBlockingService(
    private val channel: Channel,
    private val grpc: BlockchainEventsServiceBlockingStub = BlockchainEventsServiceGrpc.newBlockingStub(channel),
) : BlockchainEventsService {

    override fun events(request: WeSubscribeOnRequest): BlockchainEventsIterator =
        catchingNodeCall {
            eventsOrThrow(request.startFrom, request.filters.map { it.dto() })
                .let { grpcEventsCall ->
                    GrpcWeBlockchainEventsIterator(
                        call = grpcEventsCall.call,
                        iterator = grpcEventsCall.iterator.map { it.domain() },
                    )
                }
        }

    @Suppress("TooGenericExceptionCaught")
    private fun eventsOrThrow(
        startFrom: StartFrom,
        protoEventsFilters: List<UtilEventsSubscribeOnRequest.EventsFilter>,
    ): GrpcEventsCall =
        try {
            when (startFrom) {
                is StartFrom.Genesis -> eventsFromGenesis(protoEventsFilters)
                is StartFrom.BlockSignature -> eventsFromBlockSignature(
                    signature = blockSignature(startFrom.signature),
                    filters = protoEventsFilters,
                )

                is StartFrom.Current -> eventsFromCurrentEvent(protoEventsFilters)
            }
        } catch (ex: Exception) {
            throw EventReceivingException(
                message = "Cannot subscribe to events",
                cause = ex,
            )
        }

    private fun eventsFromGenesis(
        filters: List<UtilEventsSubscribeOnRequest.EventsFilter>,
    ): GrpcEventsCall =
        grpcEventsCall(
            SubscribeOnRequest.newBuilder()
                .setGenesisBlock(GENESIS_BLOCK)
                .addAllEventsFilters(filters)
                .build(),
        )

    private fun eventsFromBlockSignature(
        signature: UtilEventsSubscribeOnRequest.BlockSignature,
        filters: List<UtilEventsSubscribeOnRequest.EventsFilter>,
    ): GrpcEventsCall =
        grpcEventsCall(
            SubscribeOnRequest.newBuilder()
                .setBlockSignature(signature)
                .addAllEventsFilters(filters)
                .build(),
        )

    private fun eventsFromCurrentEvent(
        filters: List<UtilEventsSubscribeOnRequest.EventsFilter>,
    ): GrpcEventsCall =
        grpcEventsCall(
            SubscribeOnRequest.newBuilder()
                .setCurrentEvent(CURRENT_EVENT)
                .addAllEventsFilters(filters)
                .build(),
        )

    private fun grpcEventsCall(
        request: SubscribeOnRequest,
    ): GrpcEventsCall {
        println()
        val call: ClientCall<SubscribeOnRequest, BlockchainEvent> =
            grpc.channel.newCall(BlockchainEventsServiceGrpc.getSubscribeOnMethod(), CallOptions.DEFAULT)
        val iterator: ProtoEventsIterator = ClientCalls.blockingServerStreamingCall(call, request)
        return GrpcEventsCall(
            call = call,
            iterator = iterator,
        )
    }

    private class GrpcEventsCall(
        val call: ClientCall<SubscribeOnRequest, BlockchainEvent>,
        val iterator: ProtoEventsIterator,
    )

    private fun blockSignature(signature: Signature): UtilEventsSubscribeOnRequest.BlockSignature =
        signature.bytesValue()
            .let { UtilEventsSubscribeOnRequest.BlockSignature.newBuilder().setLastBlockSignature(it).build() }

    class GrpcWeBlockchainEventsIterator(
        private val call: ClientCall<SubscribeOnRequest, BlockchainEvent>,
        private val iterator: Iterator<WeBlockchainEvent>,
    ) : BlockchainEventsIterator, Iterator<WeBlockchainEvent> by iterator {
        override fun close() {
            call.cancel("Closed by user", null)
        }
    }

    companion object {
        private val GENESIS_BLOCK = GenesisBlock.newBuilder().build()
        private val CURRENT_EVENT = CurrentEvent.newBuilder().build()
    }
}
