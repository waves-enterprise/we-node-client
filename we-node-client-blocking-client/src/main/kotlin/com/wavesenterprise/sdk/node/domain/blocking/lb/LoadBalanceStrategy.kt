package com.wavesenterprise.sdk.node.domain.blocking.lb

import java.lang.reflect.Method

interface LoadBalanceStrategy {

    fun resolve(
        method: Method,
        args: Array<out Any>?,
    ): List<ClientWithArgs>
}
