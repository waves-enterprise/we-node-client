package com.wavesenterprise.sdk.node.client.grpc.blocking

class GrpcNodeClientParams(
    override val address: String,
    override val port: Int,
    override val keepAliveTime: Long?,
    override val keepAliveWithoutCalls: Boolean?,
) : GrpcProperties
