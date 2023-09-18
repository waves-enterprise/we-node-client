package com.wavesenterprise.sdk.node.client.grpc.blocking

interface GrpcProperties {
    val address: String
    val port: Int
    val keepAliveTime: Long?
    val keepAliveWithoutCalls: Boolean?
}
