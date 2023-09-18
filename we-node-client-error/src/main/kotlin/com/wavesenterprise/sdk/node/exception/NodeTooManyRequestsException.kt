package com.wavesenterprise.sdk.node.exception

class NodeTooManyRequestsException(
    override val cause: Exception,
) : NodeException(cause)
