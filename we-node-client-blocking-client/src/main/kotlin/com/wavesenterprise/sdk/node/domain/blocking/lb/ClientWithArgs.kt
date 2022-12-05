package com.wavesenterprise.sdk.node.domain.blocking.lb

class ClientWithArgs(
    val client: NodeServiceFactoryWrapper,
    val methodCallArgs: Array<out Any>?,
)
