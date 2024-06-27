package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.client.blocking.cache.LoadingCache
import com.wavesenterprise.sdk.node.client.blocking.lb.exception.UnknownUpdatePolicyOpTypeException
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PolicyId.Companion.policyId
import com.wavesenterprise.sdk.node.domain.sign.CreatePolicySignRequest
import com.wavesenterprise.sdk.node.domain.sign.UpdatePolicySignRequest
import com.wavesenterprise.sdk.node.domain.tx.CreatePolicyTx
import com.wavesenterprise.sdk.node.domain.tx.UpdatePolicyTx
import java.lang.reflect.Method
import java.util.Collections.addAll

private const val SIGN_METHODS_PREFIX = "sign"

class RecipientsCacheLoadBalancerPostInvokeHandler(
    private val recipientsCache: LoadingCache<PolicyId, Set<Address>>,
) : LoadBalancerPostInvokeHandler {
    override fun handle(
        client: NodeServiceFactoryWrapper,
        method: Method,
        args: Array<out Any>?,
        result: Any?,
    ) {
        if (method.name.startsWith(SIGN_METHODS_PREFIX)) {
            when (args?.getOrNull(0)) {
                is CreatePolicySignRequest -> if (result is CreatePolicyTx) {
                    recipientsCache.put(result.id.policyId, result.recipients.toMutableSet())
                }
                is UpdatePolicySignRequest -> if (result is UpdatePolicyTx) {
                    recipientsCache.getIfPresent(result.policyId)?.also { recipientsByPolicy ->
                        when (result.opType) {
                            OpType.ADD -> recipientsByPolicy.toMutableSet().apply { addAll(result.recipients) }
                            OpType.REMOVE -> recipientsByPolicy.toMutableSet()
                                .apply { removeAll(result.recipients.toSet()) }

                            OpType.UNKNOWN ->
                                throw UnknownUpdatePolicyOpTypeException("Unknown opType: ${result.opType}")
                        }.also { recipientsCache.put(result.policyId, it) }
                    }
                }
            }
        }
    }
}
