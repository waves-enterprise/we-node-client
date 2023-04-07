package com.wavesenterprise.sdk.node.client.grpc.blocking.event

import com.wavesenterprise.protobuf.service.messagebroker.BlockchainEventsServiceGrpc
import com.wavesenterprise.protobuf.service.messagebroker.MessagebrokerBlockchainEvent
import com.wavesenterprise.sdk.node.client.blocking.event.BlockchainEventsIterator
import com.wavesenterprise.sdk.node.client.blocking.event.BlockchainEventsService
import com.wavesenterprise.sdk.node.client.grpc.blocking.GrpcNodeErrorMapper
import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest

private typealias ProtoEventsIterator = Iterator<MessagebrokerBlockchainEvent.BlockchainEvent>

class BlockchainEventsGrpcBlockingService(
    private val grpc: BlockchainEventsServiceGrpc.BlockchainEventsServiceBlockingStub,
//    private val grpcStringMapper: GrpcStringMapper,
    private val grpcNodeErrorMapper: GrpcNodeErrorMapper,
//    private val grpcEventsFilterMapper: GrpcEventsFilterMapper,
//    private val grpcBlockchainEventMapper: GrpcBlockchainEventMapper
) : BlockchainEventsService {

    override fun events(request: SubscribeOnRequest): BlockchainEventsIterator {
//        return eventsOrThrow(request.startFrom, request.filters.map { grpcEventsFilterMapper.toProto(it) })
//            .let { grpcEventsCall ->
//                GrpcWeBlockchainEventsIterator(
//                    grpcEventsCall.call,
//                    grpcEventsCall.iterator
//                        .map(grpcBlockchainEventMapper::toDomain)
//                        .mapException { ex -> mapException(ex, request.startFrom) }
//                )
//            }
        TODO("Not yet implemented")
    }

//    private fun mapException(
//        ex: Exception,
//        startFrom: StartFrom
//    ): Exception {
//        fun defaultEventReceivingException(): Exception =
//            EventReceivingException(
//                message = "Cannot receive event",
//                cause = ex
//            )
//        return when (ex) {
//            is StatusRuntimeException -> {
//                when (ex.status) {
//                    NOT_FOUND -> BlockNotFoundSubscriptionException(
//                        signature = (startFrom as? StartFrom.BlockSignature)?.signature,
//                        cause = ex
//                    )
//                    UNAVAILABLE -> NodeUnavailableException(cause = ex)
//                    else -> defaultEventReceivingException()
//                }
//            }
//            else -> defaultEventReceivingException()
//        }
//    }
//
//    private fun eventsOrThrow(
//        startFrom: StartFrom,
//        protoEventsFilters: List<UtilEventsSubscribeOnRequest.EventsFilter>
//    ): GrpcEventsCall =
//        try {
//            when (startFrom) {
//                StartFrom.Genesis -> eventsFromGenesis(protoEventsFilters)
//                is StartFrom.BlockSignature -> eventsFromBlockSignature(blockSignature(startFrom.signature), protoEventsFilters)
//                StartFrom.Current -> eventsFromCurrentEvent(protoEventsFilters)
//            }
//        } catch (ex: Exception) {
//            throw EventReceivingException(
//                message = "Cannot subscribe to events",
//                cause = ex
//            )
//        }
//
//    private fun eventsFromGenesis(
//        filters: List<UtilEventsSubscribeOnRequest.EventsFilter>
//    ): GrpcEventsCall =
//        grpcEventsCall(
//            UtilEventsSubscribeOnRequest.SubscribeOnRequest.newBuilder()
//                .setGenesisBlock(GENESIS_BLOCK)
//                .addAllEventsFilters(filters)
//                .build()
//        )
//
//    private fun eventsFromBlockSignature(
//        signature: UtilEventsSubscribeOnRequest.BlockSignature,
//        filters: List<UtilEventsSubscribeOnRequest.EventsFilter>
//    ): GrpcEventsCall =
//        grpcEventsCall(
//            UtilEventsSubscribeOnRequest.SubscribeOnRequest.newBuilder()
//                .setBlockSignature(signature)
//                .addAllEventsFilters(filters)
//                .build()
//        )
//
//    private fun eventsFromCurrentEvent(
//        filters: List<UtilEventsSubscribeOnRequest.EventsFilter>
//    ): GrpcEventsCall =
//        grpcEventsCall(
//            UtilEventsSubscribeOnRequest.SubscribeOnRequest.newBuilder()
//                .setCurrentEvent(CURRENT_EVENT)
//                .addAllEventsFilters(filters)
//                .build()
//        )
//
//    private fun grpcEventsCall(
//        request: UtilEventsSubscribeOnRequest.SubscribeOnRequest
//    ): GrpcEventsCall {
//        val call: ClientCall<UtilEventsSubscribeOnRequest.SubscribeOnRequest, MessagebrokerBlockchainEvent.BlockchainEvent> =
//            grpc.channel.newCall(BlockchainEventsServiceGrpc.getSubscribeOnMethod(), CallOptions.DEFAULT)
//        val iterator: ProtoEventsIterator = ClientCalls.blockingServerStreamingCall(call, request)
//        return GrpcEventsCall(call, iterator)
//    }
//
//    private class GrpcEventsCall(
//        val call: ClientCall<UtilEventsSubscribeOnRequest.SubscribeOnRequest, MessagebrokerBlockchainEvent.BlockchainEvent>,
//        val iterator: ProtoEventsIterator
//    )
//
//    private fun blockSignature(signature: Signature): UtilEventsSubscribeOnRequest.BlockSignature =
//        signature
//            .let(grpcStringMapper::fromBase58ToBytesValue)
//            .let { UtilEventsSubscribeOnRequest.BlockSignature.newBuilder().setLastBlockSignature(it).build() }
//
//    private class GrpcWeBlockchainEventsIterator(
//        private val call: ClientCall<UtilEventsSubscribeOnRequest.SubscribeOnRequest, BlockchainEvent>,
//        private val iterator: Iterator<BlockchainEvent>
//    ) : BlockchainEventsIterator, Iterator<BlockchainEvent> by iterator {
//        override fun close() {
//            call.cancel("Closed by user", null)
//        }
//    }
//
//    companion object {
//        private val GENESIS_BLOCK = UtilEventsSubscribeOnRequest.GenesisBlock.newBuilder().build()
//        private val CURRENT_EVENT = UtilEventsSubscribeOnRequest.CurrentEvent.newBuilder().build()
//    }
}
