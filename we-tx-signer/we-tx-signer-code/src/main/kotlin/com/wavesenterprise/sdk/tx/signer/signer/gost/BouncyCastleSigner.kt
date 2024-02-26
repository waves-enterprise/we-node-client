package com.wavesenterprise.sdk.tx.signer.signer.gost

import com.wavesenterprise.sdk.node.domain.PublicKey.Companion.publicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Signature.Companion.signature
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxId.Companion.txId
import com.wavesenterprise.sdk.node.domain.util.ADDRESS_VERSION
import org.bouncycastle.jcajce.provider.asymmetric.ecgost.BCECGOST3410PublicKey
import org.bouncycastle.jcajce.provider.digest.GOST3411
import org.bouncycastle.jce.ECGOST3410NamedCurveTable
import org.bouncycastle.jce.interfaces.ECPrivateKey
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECPublicKeySpec
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.Security

class BouncyCastleSigner(
    private val privateKey: PrivateKey,
    private val networkByte: Byte,
) : GOSTSigner {

    init {
        Security.addProvider(BouncyCastleProvider())
    }

    override fun getTxId(txBytes: ByteArray): TxId = GOST3411.Digest2012_256().digest(txBytes).txId

    override fun getSignature(txBytes: ByteArray): Signature {
        val signer = java.security.Signature.getInstance("ECGOST3410-2012-256", "BC")
        signer.initSign(privateKey)
        signer.update(txBytes)
        return signer.sign().signature
    }

    override fun getPublicKey(): com.wavesenterprise.sdk.node.domain.PublicKey {
        val curveParams = ECGOST3410NamedCurveTable.getParameterSpec("GostR3410-2001-CryptoPro-A")
        val keyFactory = KeyFactory.getInstance("ECGOST3410", "BC")
        val ecPublicKeySpec = ECPublicKeySpec(
            curveParams.g.multiply((privateKey as ECPrivateKey).d),
            curveParams
        )
        val pubKey = keyFactory.generatePublic(ecPublicKeySpec) as BCECGOST3410PublicKey
        return pubKey.encoded.drop(pubKey.encoded.size - ASN1_KEY_PREFIX_SIZE).toByteArray().publicKey
    }

    @Suppress("MagicNumber")
    override fun createAddress(publicKeyBytes: ByteArray): ByteArray {
        val prefix = byteArrayOf(ADDRESS_VERSION, networkByte)
        val publicKeyHashPart = GOST3411.Digest2012_256().digest(publicKeyBytes).copyOfRange(0, 20)
        val rawAddress = prefix + publicKeyHashPart
        val addressHash = GOST3411.Digest2012_256().digest(rawAddress).copyOfRange(0, 4)
        return rawAddress + addressHash
    }

    override fun getNetworkByte(): Byte = networkByte

    companion object {
        private const val ASN1_KEY_PREFIX_SIZE = 64
    }
}
