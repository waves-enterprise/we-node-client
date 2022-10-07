package com.wavesenterprise.sdk.tx.signer

import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx

interface TxSigner {
    fun <T : Tx> sign(signRequest: SignRequest<T>): T
}
