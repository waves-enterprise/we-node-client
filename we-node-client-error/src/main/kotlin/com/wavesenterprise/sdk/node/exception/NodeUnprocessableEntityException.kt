package com.wavesenterprise.sdk.node.exception

class NodeUnprocessableEntityException(
    override val cause: Exception,
) : NodeException(cause)
