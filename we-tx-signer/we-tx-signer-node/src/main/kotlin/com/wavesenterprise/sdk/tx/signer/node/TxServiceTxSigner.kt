package com.wavesenterprise.sdk.tx.signer.node

import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.tx.signer.TxSigner
import com.wavesenterprise.sdk.tx.signer.node.credentials.SignCredentialsProvider

class TxServiceTxSigner(
    private val txService: TxService,
    private val signCredentialsProvider: SignCredentialsProvider,
) : TxSigner {
    override fun <T : Tx> sign(signRequest: SignRequest<T>): T = txService.sign(
        signRequest
            .withAddress(signCredentialsProvider.credentials().senderAddress)
            .withPassword(signCredentialsProvider.credentials().password)
    )
}
