package com.wavesenterprise.sdk.node.exception

class NodeInternalServerErrorException(
    override val cause: Exception,
) : NodeException(cause)
