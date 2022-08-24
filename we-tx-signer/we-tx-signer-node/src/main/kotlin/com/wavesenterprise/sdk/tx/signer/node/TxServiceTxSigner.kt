package com.wavesenterprise.sdk.tx.signer.node

import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.tx.signer.TxSigner

class TxServiceTxSigner(
    private val txService: TxService,
    private val signCredentialsService: SignCredentialsService,
) : TxSigner {
    override fun <T : Tx> sign(signRequest: SignRequest<T>): T = txService.sign(
        signRequest
            .withAddress(signCredentialsService.senderAddress())
            .withPassword(signCredentialsService.password())
    )
}
