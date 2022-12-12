package com.wavesenterprise.sdk.node.exception

class NodeUnauthorizedException(
    override val cause: Exception,
) : NodeException(cause)
