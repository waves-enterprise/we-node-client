package com.wavesenterprise.sdk.tx.signer.signer.gost

import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.PublicKey.Companion.publicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Signature.Companion.signature
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxId.Companion.txId
import com.wavesenterprise.sdk.node.domain.util.ADDRESS_VERSION
import ru.CryptoPro.JCP.JCP
import ru.CryptoPro.JCSP.JCSP
import ru.CryptoPro.JCSP.Key.GostExchPrivateKey
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.Security

class CryptoProSigner(
    private val privateKey: PrivateKey,
    private val networkByte: Byte,
) : GOSTSigner {

    init {
        Security.addProvider(JCSP())
    }

    private val gostDigest: MessageDigest by lazy {
        MessageDigest.getInstance(JCP.GOST_DIGEST_2012_256_NAME, JCSP.PROVIDER_NAME)
    }

    private val signature: java.security.Signature by lazy {
        java.security.Signature.getInstance(JCP.GOST_SIGN_2012_256_NAME, JCSP.PROVIDER_NAME)
    }

    override fun getTxId(txBytes: ByteArray): TxId =
        gostDigest.digest(txBytes).txId

    override fun getSignature(txBytes: ByteArray): Signature {
        signature.initSign(privateKey)
        signature.update(txBytes)
        return signature.sign().signature
    }

    override fun getPublicKey(): PublicKey {
        val key = privateKey as GostExchPrivateKey
        val spec = key.spec
        val alg = key.algorithm
        val kf = KeyFactory.getInstance(alg)
        val keyWithAsn1 = kf.generatePublic(spec).encoded
        return keyWithAsn1.drop(keyWithAsn1.size - ASN1_KEY_PREFIX_SIZE).toByteArray().publicKey
    }

    @Suppress("MagicNumber")
    override fun createAddress(publicKeyBytes: ByteArray): ByteArray {
        val prefix = byteArrayOf(ADDRESS_VERSION, networkByte)
        val publicKeyHashPart = gostDigest.digest(publicKeyBytes).copyOfRange(0, 20)
        val rawAddress = prefix + publicKeyHashPart
        val addressHash = gostDigest.digest(rawAddress).copyOfRange(0, 4)
        return rawAddress + addressHash
    }

    override fun getNetworkByte(): Byte = networkByte

    companion object {
        private const val ASN1_KEY_PREFIX_SIZE = 64
    }
}
