package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import java.lang.reflect.Method

class DefaultLoadBalanceStrategy(
    private val nodesResolver: NodesResolver,
) : LoadBalanceStrategy {

    // TODO: Map privacy methods
    override fun resolve(method: Method, args: Array<out Any>?): List<ClientWithArgs> =
        when (val firstArg = args?.firstOrNull()) {
            is SignRequest<*> -> nodesResolver.getOrderedAliveNodesForSign(firstArg)
            else -> nodesResolver.getOrderedAliveNodes()
        }.map { node ->
            ClientWithArgs(
                client = node,
                methodCallArgs = args,
            )
        }
}
