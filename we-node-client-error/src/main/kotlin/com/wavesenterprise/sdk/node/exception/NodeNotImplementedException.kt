package com.wavesenterprise.sdk.node.exception

class NodeNotImplementedException(
    override val cause: Exception,
) : NodeException(cause)
