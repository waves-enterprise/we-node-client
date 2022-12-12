package com.wavesenterprise.sdk.node.exception

class NodeUnexpectedException(
    override val cause: Exception,
) : NodeException(cause)
