package com.wavesenterprise.sdk.node.client.blocking.lb

import java.lang.reflect.Method

interface LoadBalancerPostInvokeHandler {

    fun handle(
        client: NodeServiceFactoryWrapper,
        method: Method,
        args: Array<out Any>?,
        result: Any?,
    )
}
