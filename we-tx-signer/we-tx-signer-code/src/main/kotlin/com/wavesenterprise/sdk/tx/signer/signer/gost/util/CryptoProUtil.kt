package com.wavesenterprise.sdk.tx.signer.signer.gost.util

import ru.CryptoPro.JCP.JCP
import ru.CryptoPro.JCP.KeyStore.JCPPrivateKeyEntry
import ru.CryptoPro.JCP.params.CryptDhAllowedSpec
import ru.CryptoPro.JCPRequest.GostCertificateRequest
import ru.CryptoPro.JCSP.JCSP
import java.io.ByteArrayInputStream
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.cert.Certificate
import java.security.cert.CertificateFactory

object CryptoProUtil {
    private const val keyAlgorithm = JCP.GOST_DH_2012_256_NAME
    private const val providerName = JCSP.PROVIDER_NAME
    private const val storeName = JCSP.HD_STORE_NAME

    private const val dName = "CN=Waves_2012_256, O=Waves, C=RU"

    private val keyPairGenerator: KeyPairGenerator = KeyPairGenerator.getInstance(keyAlgorithm, providerName).also {
        it.initialize(CryptDhAllowedSpec())
    }

    private val keyStore: KeyStore = KeyStore.getInstance(storeName, providerName)

    fun generateAndStoreKeyPair(password: String, alias: String): KeyPair {
        keyStore.load(null, null)
        val keyPair = keyPairGenerator.generateKeyPair()
        storeKey(
            keyPair = keyPair,
            password = password,
            alias = alias,
        )
        return keyPair
    }

    fun getPublicKey(alias: String): PublicKey = keyStore.getCertificate(alias).publicKey

    fun getPrivateKey(password: String, alias: String): PrivateKey =
        (keyStore.getEntry(alias, KeyStore.PasswordProtection(password.toCharArray())) as JCPPrivateKeyEntry).privateKey

    fun getKeyPair(password: String, alias: String): KeyPair = KeyPair(
        getPublicKey(alias),
        getPrivateKey(password, alias)
    )

    fun deleteEntry(alias: String) = keyStore.deleteEntry(alias)

    private fun storeKey(keyPair: KeyPair, password: String, alias: String) {
        val certificates = arrayOf(genSelfCert(keyPair))
        val entry = JCPPrivateKeyEntry(keyPair.private, certificates)
        val protection = KeyStore.PasswordProtection(password.toCharArray())
        keyStore.setEntry(alias, entry, protection)
    }

    private fun genSelfCert(pair: KeyPair): Certificate {
        val gr = GostCertificateRequest(providerName)
        val enc = gr.getEncodedSelfCert(pair, dName)
        val cf = CertificateFactory.getInstance(JCP.CERTIFICATE_FACTORY_NAME)
        return cf.generateCertificate(ByteArrayInputStream(enc))
    }
}
