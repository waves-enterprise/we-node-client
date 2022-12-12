package com.wavesenterprise.sdk.node.exception

class NodeServiceUnavailableException(
    override val cause: Exception,
) : NodeException(cause)
