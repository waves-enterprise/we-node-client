package com.wavesenterprise.sdk.node.exception

class NodeForbiddenException(
    override val cause: Exception,
) : NodeException(cause)
