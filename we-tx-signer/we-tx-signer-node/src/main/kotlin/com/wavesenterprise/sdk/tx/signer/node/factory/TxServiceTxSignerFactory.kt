package com.wavesenterprise.sdk.tx.signer.node.factory

import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService
import com.wavesenterprise.sdk.tx.signer.TxSigner
import com.wavesenterprise.sdk.tx.signer.node.TxServiceTxSigner
import com.wavesenterprise.sdk.tx.signer.node.credentials.Credentials
import com.wavesenterprise.sdk.tx.signer.node.credentials.DefaultSignCredentialsProvider

class TxServiceTxSignerFactory(
    private val txService: TxService,
) {
    fun withCredentials(credentials: Credentials): TxSigner =
        TxServiceTxSigner(
            txService = txService,
            signCredentialsProvider = DefaultSignCredentialsProvider(credentials),
        )
}
