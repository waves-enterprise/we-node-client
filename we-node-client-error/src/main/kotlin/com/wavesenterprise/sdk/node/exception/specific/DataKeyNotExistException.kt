package com.wavesenterprise.sdk.node.exception.specific

import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.NodeErrorInfoHolder
import com.wavesenterprise.sdk.node.exception.NodeException

class DataKeyNotExistException(
    override val nodeError: NodeError,
    override val cause: Exception,
) : NodeException(cause), NodeErrorInfoHolder
