package com.wavesenterprise.sdk.tx.signer

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Address.Companion.EMPTY
import com.wavesenterprise.sdk.node.domain.Address.Companion.address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.tx.signer.signer.Signer

class SelfTxSigner(
    private val signer: Signer,
) : TxSigner {

    @Suppress("UNCHECKED_CAST")
    override fun <T : Tx> sign(signRequest: SignRequest<T>): T {
        val senderPublicKey: PublicKey = signer.getPublicKey()
        val networkByte = signer.getNetworkByte()
        val tx = signRequest.mapToTx(
            senderPublicKey = senderPublicKey,
            chainId = ChainId(networkByte),
        )
        val txBytes = tx.getBytes(networkByte)
        val txId = signer.getTxId(txBytes)
        val signature = signer.getSignature(txBytes)
        val senderAddress: Address =
            if (signRequest.senderAddress == EMPTY)
                signer.createAddress(senderPublicKey.bytes).address
            else
                signRequest.senderAddress
        return tx
            .withId(txId)
            .withProof(signature)
            .withSenderAddress(senderAddress) as T
    }

    override fun getSignerAddress(): Address {
        TODO("https://jira.web3tech.ru/browse/WTCH-213")
    }
}
