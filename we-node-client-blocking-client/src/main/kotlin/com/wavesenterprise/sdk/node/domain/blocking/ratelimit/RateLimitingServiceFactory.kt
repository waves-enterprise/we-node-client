package com.wavesenterprise.sdk.node.domain.blocking.ratelimit

import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.domain.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.sdk.node.domain.tx.Tx

class RateLimitingServiceFactory(
    private val nodeBlockingServiceFactory: NodeBlockingServiceFactory,
    private val rateLimiter: RateLimiter,
) : NodeBlockingServiceFactory by nodeBlockingServiceFactory {

    override fun txService(): TxService {
        val txService = nodeBlockingServiceFactory.txService()

        return object : TxService by txService {
            override fun <T : Tx> broadcast(tx: T): T {
                rateLimiter.waitIfNeeded()
                return txService.broadcast(tx)
            }

            override fun <T : Tx> signAndBroadcast(request: SignRequest<T>): T {
                rateLimiter.waitIfNeeded()
                return txService.signAndBroadcast(request)
            }
        }
    }

    override fun privacyService(): PrivacyService {
        val privacyService = nodeBlockingServiceFactory.privacyService()

        return object : PrivacyService by privacyService {
            override fun sendData(request: SendDataRequest): PolicyDataHashTx {
                if (request.broadcastTx) {
                    rateLimiter.waitIfNeeded()
                }
                return privacyService.sendData(request)
            }
        }
    }
}
