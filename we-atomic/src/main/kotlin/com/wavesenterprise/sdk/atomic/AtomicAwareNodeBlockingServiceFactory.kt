package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.sign.AtomicInnerSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.tx.signer.TxSigner

class AtomicAwareNodeBlockingServiceFactory(
    private val nodeBlockingServiceFactory: NodeBlockingServiceFactory,
    private val txSigner: TxSigner,
) : NodeBlockingServiceFactory by nodeBlockingServiceFactory {

    val atomicAwareContextManager: AtomicAwareContextManager =
        ThreadLocalAtomicAwareContextManager()

    override fun txService(): TxService = nodeBlockingServiceFactory.txService().let { txService ->
        object : TxService by txService {
            override fun <T : Tx> broadcast(tx: T): T =
                with(atomicAwareContextManager.getContext()) {
                    if (isSentInAtomic() && tx !is AtomicTx) {
                        tx.also { addTx(it) }
                    } else {
                        txService.broadcast(tx)
                    }
                }

            override fun <T : Tx> signAndBroadcast(request: SignRequest<T>): T =
                with(atomicAwareContextManager.getContext()) {
                    if (isSentInAtomic() && request is AtomicInnerSignRequest) {
                        txSigner.sign(request).also { addTx(it) }
                    } else {
                        txService.signAndBroadcast(request)
                    }
                }
        }
    }
}
