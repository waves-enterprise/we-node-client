package com.wavesenterprise.sdk.node.client.grpc.blocking.factory

import com.wavesenterprise.sdk.node.client.grpc.blocking.GrpcNodeClientParams
import io.grpc.Channel
import io.grpc.ClientInterceptor
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit

object GrpcNodeServiceFactoryFactory {

    @JvmStatic
    fun createClient(
        grpcProperties: GrpcNodeClientParams,
        clientInterceptors: List<ClientInterceptor> = emptyList(),
    ): GrpcNodeServiceFactory {
        val channel: Channel = buildChannel(grpcProperties = grpcProperties)
        return GrpcNodeServiceFactory(
            clientInterceptors = clientInterceptors,
            channel = channel,
        )
    }

    private fun buildChannel(grpcProperties: GrpcNodeClientParams) =
        ManagedChannelBuilder
            .forAddress(grpcProperties.address, grpcProperties.port)
            .run {
                if (grpcProperties.keepAliveTime != null) {
                    keepAliveTime(grpcProperties.keepAliveTime, TimeUnit.MILLISECONDS)
                } else {
                    this
                }
            }
            .run {
                if (grpcProperties.keepAliveWithoutCalls != null) {
                    keepAliveWithoutCalls(grpcProperties.keepAliveWithoutCalls)
                } else {
                    this
                }
            }
            .usePlaintext()
            .build()
}
