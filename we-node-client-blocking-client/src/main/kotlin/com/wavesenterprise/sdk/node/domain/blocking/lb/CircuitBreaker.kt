package com.wavesenterprise.sdk.node.domain.blocking.lb

interface CircuitBreaker {
    val nodeCircuitBreakers: Map<String, NodeCircuitBreaker>

    fun invocationFailed(nodeName: String, index: Int)
    fun tryReturnIntoRotation(nodeName: String)
    fun isClosed(nodeName: String): Boolean
}
