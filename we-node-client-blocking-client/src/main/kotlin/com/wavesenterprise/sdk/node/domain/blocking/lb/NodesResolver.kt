package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx

interface NodesResolver {
    fun getOrderedAliveNodes(): List<NodeServiceFactoryWrapper>

    fun getOrderedAliveNodesForSign(sign: SignRequest<out Tx>): List<NodeServiceFactoryWrapper>
}
