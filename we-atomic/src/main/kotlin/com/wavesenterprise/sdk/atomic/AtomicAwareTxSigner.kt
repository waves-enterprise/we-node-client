package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.sign.AtomicInnerSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.tx.signer.TxSigner

class AtomicAwareTxSigner(
    private val atomicAwareContextManager: AtomicAwareContextManager,
    private val txSigner: TxSigner,
) : TxSigner by txSigner {
    override fun <T : Tx> sign(signRequest: SignRequest<T>): T =
        with(atomicAwareContextManager.getContext()) {
            var signRequestWithAtomicBadge = signRequest
            if (isSentInAtomic()) {
                when (signRequest) {
                    is AtomicInnerSignRequest -> {
                        signRequestWithAtomicBadge = signRequest.withAtomicBadge(
                            AtomicBadge(
                                trustedSender = getSignerAddress()
                            )
                        )
                    }
                    else -> throw IllegalArgumentException("not atomic signable")
                }
            }
            txSigner.sign(signRequestWithAtomicBadge)
        }
}
