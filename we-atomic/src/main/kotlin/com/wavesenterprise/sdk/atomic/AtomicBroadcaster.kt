package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.tx.signer.TxSigner

class AtomicBroadcaster(
    // todo: rename
    private val txSigner: TxSigner,
    private val atomicAwareContextManager: AtomicAwareContextManager,
    private val atomicAwareNodeBlockingServiceFactory: NodeBlockingServiceFactory,
) {
    private val txService: TxService = atomicAwareNodeBlockingServiceFactory.txService()

    fun <T> doInAtomic(block: () -> T): T? {
        atomicAwareContextManager.beginAtomic()
        try {
            val blockResult = block()
            val atomicSignRequest = atomicAwareContextManager.commitAtomic()
            return if (atomicSignRequest.txs.isNotEmpty()) {
                val signedAtomicSignRequest = txSigner.sign(atomicSignRequest)
                txService.broadcast(signedAtomicSignRequest)
                return blockResult
            } else blockResult
        } finally {
            atomicAwareContextManager.clearContext()
        }
    }
}
