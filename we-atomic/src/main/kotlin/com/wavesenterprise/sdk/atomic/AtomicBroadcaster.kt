package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.sdk.tx.signer.TxSigner

class AtomicBroadcaster( // todo: rename
    private val txSigner: TxSigner,
    private val atomicAwareContextManager: AtomicAwareContextManager,
    private val atomicAwareNodeBlockingServiceFactory: NodeBlockingServiceFactory,
) {
    private val txService: TxService = atomicAwareNodeBlockingServiceFactory.txService()

    fun doInAtomic(block: () -> Unit): AtomicTx? {
        atomicAwareContextManager.beginAtomic()
        try {
            block()
            val atomicSignRequest = atomicAwareContextManager.commitAtomic()
            if (atomicSignRequest.txs.isEmpty()) {
                return null
            }
            val signedAtomicSignRequest = txSigner.sign(atomicSignRequest)
            return txService.broadcast(signedAtomicSignRequest)
        } finally {
            atomicAwareContextManager.clearContext()
        }
    }
}
