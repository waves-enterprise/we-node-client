package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.atomic.manager.AtomicAwareContextManager
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
            val signRequestResult = if (isSentInAtomic()) {
                when (signRequest) {
                    is AtomicInnerSignRequest -> {
                        signRequest.withAtomicBadge(
                            AtomicBadge(
                                trustedSender = getSignerAddress(),
                            ),
                        )
                    }
                    else -> throw IllegalArgumentException("Not atomic signable: $signRequest")
                }
            } else {
                signRequest
            }
            txSigner.sign(signRequestResult)
        }
}
