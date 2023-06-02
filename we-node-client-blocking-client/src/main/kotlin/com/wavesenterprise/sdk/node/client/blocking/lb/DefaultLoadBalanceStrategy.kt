package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.client.blocking.credentials.NodeCredentialsProvider
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import java.lang.reflect.Method

class DefaultLoadBalanceStrategy(
    private val nodesResolver: NodesResolver,
    private val nodeCredentialsProvider: NodeCredentialsProvider,
) : LoadBalanceStrategy {

    override fun resolve(method: Method, args: Array<out Any>?): List<ClientWithArgs> =
        when (val firstArg = args?.firstOrNull()) {
            is SendDataRequest ->
                nodesResolver.getOrderedAliveNodesWithCredsForPrivacy(firstArg.policyId).map { client ->
                    val senderAddress = client.getNodeOwner().address
                    ClientWithArgs(
                        client = client,
                        methodCallArgs = args.patch { methodCallArgs ->
                            methodCallArgs[0] = firstArg.copy(
                                senderAddress = senderAddress,
                                password = nodeCredentialsProvider.getPassword(senderAddress),
                            )
                        }
                    )
                }
            else -> {
                when (firstArg) {
                    is SignRequest<*> -> nodesResolver.getOrderedAliveNodesForSign(firstArg)
                    is PolicyId -> {
                        when (method.name) {
                            GET_PRIVACY_INFO_METHOD_NAME ->
                                nodesResolver.getOrderedAliveNodesForPrivacyInfo(firstArg)
                            GET_PRIVACY_DATA_METHOD_NAME -> {
                                val secondArg = Hash.fromStringBase58(args[1] as String)
                                nodesResolver.getOrderedAliveNodesForPrivacyData(firstArg, secondArg)
                            }
                            else -> nodesResolver.getOrderedAliveNodes()
                        }
                    }
                    else -> nodesResolver.getOrderedAliveNodes()
                }.map { node ->
                    ClientWithArgs(
                        client = node,
                        methodCallArgs = args,
                    )
                }
            }
        }

    private companion object {
        private val GET_PRIVACY_INFO_METHOD_NAME = PrivacyService::info.name
        private val GET_PRIVACY_DATA_METHOD_NAME = PrivacyService::data.name

        @JvmStatic
        inline fun Array<out Any>.patch(block: (Array<Any>) -> Unit): Array<Any> =
            Array(size) { this[it] }.apply(block)
    }
}
