package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import java.lang.reflect.Method

class PrivacyDataNodesCacheLoadBalancerPostInvokeHandler(
    private val privacyDataNodesCache: PrivacyDataNodesCache,
) : LoadBalancerPostInvokeHandler {

    companion object {
        private val GET_PRIVACY_INFO_METHOD_NAME = PrivacyService::info.name
        private val SEND_DATA_METHOD_NAME = PrivacyService::sendData.name

        private const val BROADCAST_METHOD_NAME = "broadcast"
        private const val SIGN_AND_BROADCAST_METHOD_NAME = "signAndBroadcast"
    }

    override fun handle(
        client: NodeServiceFactoryWrapper,
        method: Method,
        args: Array<out Any>?,
        result: Any?,
    ) {
        when (method.name) {
            GET_PRIVACY_INFO_METHOD_NAME -> {
                checkNotNull(args)
                val policyItemRequest = args[0] as PolicyItemRequest
                privacyDataNodesCache.put(
                    policyId = policyItemRequest.policyId,
                    policyDataHash = policyItemRequest.dataHash,
                    alias = client.name,
                )
            }
            BROADCAST_METHOD_NAME, SIGN_AND_BROADCAST_METHOD_NAME -> {
                if (result is AtomicTx) {
                    result.txs.filterIsInstance(PolicyDataHashTx::class.java).forEach { policyDataHashTx ->
                        privacyDataNodesCache.put(
                            policyId = policyDataHashTx.policyId,
                            policyDataHash = policyDataHashTx.dataHash,
                            alias = client.name,
                        )
                    }
                }
            }
            SEND_DATA_METHOD_NAME -> {
                checkNotNull(args)
                val sendDataRequest = args[0] as SendDataRequest
                if (sendDataRequest.broadcastTx) {
                    privacyDataNodesCache.put(
                        policyId = sendDataRequest.policyId,
                        policyDataHash = sendDataRequest.dataHash,
                        alias = client.name,
                    )
                }
            }
        }
    }
}
