package com.wavesenterprise.sdk.node.client.blocking.lb

class ClientWithArgs(
    val client: NodeServiceFactoryWrapper,
    val methodCallArgs: Array<out Any>?,
)
