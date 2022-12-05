package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx

class DefaultNodesResolver(
    private val nodeServiceFactoryWrappers: List<NodeServiceFactoryWrapper>,
) : NodesResolver {

    override fun getOrderedAliveNodes(): List<NodeServiceFactoryWrapper> = orderedClientsWithProperties.map { it }

    override fun getOrderedAliveNodesForSign(sign: SignRequest<out Tx>): List<NodeServiceFactoryWrapper> =
        getOrderedAliveNodes().filter {
            it.runCatching {
                getAddresses()
            }.getOrNull()?.contains(sign.senderAddress) ?: false
        }

    private val orderedClientsWithProperties
        get() = nodeServiceFactoryWrappers.filter { it.isAlive() }.shuffled()
}
