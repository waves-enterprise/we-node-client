package com.wavesenterprise.sdk.node.exception

class NodeNotFoundException(
    override val nodeError: NodeError? = null,
    override val cause: Exception,
) : NodeErrorInfoHolder, NodeException(cause)
