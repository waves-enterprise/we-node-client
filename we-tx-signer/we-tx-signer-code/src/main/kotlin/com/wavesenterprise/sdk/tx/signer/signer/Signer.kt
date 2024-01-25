package com.wavesenterprise.sdk.tx.signer.signer

import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.TxId

interface Signer {
    fun getTxId(txBytes: ByteArray): TxId
    fun getSignature(txBytes: ByteArray): Signature

    fun getPublicKey(): PublicKey

    fun createAddress(publicKeyBytes: ByteArray): ByteArray

    fun getNetworkByte(): Byte
}
