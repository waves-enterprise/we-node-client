package com.wavesenterprise.sdk.tx.signer.node

import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.tx.signer.TxSigner
import com.wavesenterprise.sdk.tx.signer.node.credentials.SignCredentialsProvider

class TxServiceTxSigner(
    private val txService: TxService,
    private val signCredentialsProvider: SignCredentialsProvider,
) : TxSigner {
    override fun <T : Tx> sign(signRequest: SignRequest<T>): T =
        with(signCredentialsProvider.credentials()) {
            checkSenderAddress(senderAddress)
            txService.sign(
                signRequest
                    .withAddress(senderAddress)
                    .withPassword(password),
            )
        }

    private fun checkSenderAddress(address: Address): Address =
        if (address == Address.EMPTY) {
            throw IllegalArgumentException("Sender address can not be empty [senderAddress = `${Address.EMPTY}`")
        } else {
            address
        }

    override fun getSignerAddress(): Address = signCredentialsProvider.credentials().senderAddress
}
