package com.wavesenterprise.sdk.node.exception

import java.lang.RuntimeException

open class NodeException(
    override val cause: Exception,
) : RuntimeException()
