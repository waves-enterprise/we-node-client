package com.wavesenterprise.tx.signer.bouncycastle.gost

import com.wavesenterprise.sdk.node.domain.util.NONCE
import com.wavesenterprise.sdk.node.domain.util.processor.numberToBytes
import com.wavesenterprise.sdk.tx.signer.DICTIONARY
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.engines.AESEngine
import org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator
import org.bouncycastle.crypto.modes.CBCBlockCipher
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher
import org.bouncycastle.crypto.params.ParametersWithIV
import org.bouncycastle.jcajce.provider.asymmetric.ecgost.BCECGOST3410PrivateKey
import org.bouncycastle.jcajce.provider.digest.GOST3411
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECPrivateKeySpec
import org.bouncycastle.jce.spec.ECPublicKeySpec
import org.bouncycastle.math.ec.ECPoint
import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.Security
import java.security.spec.ECGenParameterSpec
import java.util.Base64

object BouncyCastleUtil {
    private const val PROVIDER = "BC"
    private const val KEY_PAIR_ALG = "ECGOST3410"
    private const val EC_PARAMS = "GostR3410-2001-CryptoPro-A"

    init {
        Security.addProvider(BouncyCastleProvider())
    }

    fun generateKeyPair(): KeyPair {
        val keyPairGen = KeyPairGenerator.getInstance(KEY_PAIR_ALG, PROVIDER).also {
            it.initialize(ECGenParameterSpec(EC_PARAMS))
        }
        return keyPairGen.generateKeyPair()
    }

    fun generateKeyPairFromSeed(seed: String): KeyPair {
        val d = BigInteger(1, seedHash(seed).reversedArray())
        val ecParameterSpec = ECNamedCurveTable.getParameterSpec(EC_PARAMS)
        val privateKeySpec = ECPrivateKeySpec(d, ecParameterSpec)
        val keyFactory: KeyFactory = KeyFactory.getInstance(KEY_PAIR_ALG)
        val privateKey = keyFactory.generatePrivate(privateKeySpec) as BCECGOST3410PrivateKey
        val q: ECPoint = ecParameterSpec.g.multiply(privateKey.d)
        val spec = ECPublicKeySpec(q, ecParameterSpec)
        val publicKey = keyFactory.generatePublic(spec)
        return KeyPair(publicKey, privateKey)
    }

    fun generateKeyPairWithRandomSeed(password: String): Pair<KeyPair, String> {
        val seed = generateSeed()
        val kp = generateKeyPairFromSeed(seed)
        val encryptedPrivateKey = encryptSeedAES(seed, password)
        return Pair(kp, encryptedPrivateKey)
    }

    private fun strengthenPassword(password: String, rounds: Int = 5000): String {
        var newPassword = password
        repeat(rounds) {
            newPassword = Hex.toHexString((sha256(newPassword)))
        }
        return newPassword
    }

    private fun sha256(input: String): ByteArray {
        val bytes: ByteArray = input.toByteArray(StandardCharsets.UTF_8)
        val sha256Digest = SHA256Digest()
        sha256Digest.update(bytes, 0, bytes.size)
        val result = ByteArray(sha256Digest.digestSize)
        sha256Digest.doFinal(result, 0)
        return result
    }

    @Suppress("MagicNumber")
    fun encryptSeedAES(seed: String, password: String): String {
        val salt = generateSalt()
        val passwordBytes = strengthenPassword(password).toByteArray(StandardCharsets.UTF_8)
        val pbeGenerator = OpenSSLPBEParametersGenerator()
        pbeGenerator.init(passwordBytes, salt)
        val parameters = pbeGenerator.generateDerivedParameters(256, 128) as ParametersWithIV
        val cipher = PaddedBufferedBlockCipher(CBCBlockCipher(AESEngine()))
        cipher.init(true, parameters)
        val plaintextBytes = seed.toByteArray(StandardCharsets.UTF_8)
        val ciphertextSize = cipher.getOutputSize(plaintextBytes.size)
        val ciphertext = ByteArray(ciphertextSize)
        val length = cipher.processBytes(plaintextBytes, 0, plaintextBytes.size, ciphertext, 0)
        cipher.doFinal(ciphertext, length)
        val encryptedSeedBytes = ByteArray(8 + 8 + ciphertext.size)
        System.arraycopy("Salted__".toByteArray(StandardCharsets.UTF_8), 0, encryptedSeedBytes, 0, 8)
        System.arraycopy(salt, 0, encryptedSeedBytes, 8, 8)
        System.arraycopy(ciphertext, 0, encryptedSeedBytes, 16, ciphertext.size)

        return Base64.getEncoder().encodeToString(encryptedSeedBytes)
    }

    @Suppress("MagicNumber")
    fun decryptSeedAES(encryptedSeed: String, password: String): String {
        val saltCiphertext = Base64.getDecoder().decode(encryptedSeed)
        val byteBuffer: ByteBuffer = ByteBuffer.wrap(saltCiphertext)
        val prefix = ByteArray(8)
        val salt = ByteArray(8)
        byteBuffer.get(prefix)
        byteBuffer.get(salt)
        val ciphertext = ByteArray(byteBuffer.remaining())
        byteBuffer.get(ciphertext)
        val passwordBytes = strengthenPassword(password).toByteArray(StandardCharsets.UTF_8)
        val pbeGenerator = OpenSSLPBEParametersGenerator()
        pbeGenerator.init(passwordBytes, salt)
        val parameters = pbeGenerator.generateDerivedParameters(256, 128) as ParametersWithIV
        val cipher = PaddedBufferedBlockCipher(CBCBlockCipher(AESEngine()))
        cipher.init(false, parameters)
        val plaintext = ByteArray(cipher.getOutputSize(ciphertext.size))
        var length = cipher.processBytes(ciphertext, 0, ciphertext.size, plaintext, 0)
        length += cipher.doFinal(plaintext, length)
        return String(plaintext, 0, length, StandardCharsets.UTF_8)
    }

    @Suppress("MagicNumber")
    private fun generateSalt(): ByteArray {
        val salt = ByteArray(8)
        SecureRandom().nextBytes(salt)
        return salt
    }

    @Suppress("MagicNumber")
    private fun seedHash(seed: String): ByteArray {
        val nonceBytes = numberToBytes(NONCE, 4)
        val seedBytes = seed.toByteArray()
        val seedBytesWithNonce = nonceBytes + seedBytes
        return GOST3411.Digest2012_256().digest(seedBytesWithNonce)
    }

    fun generateSeed(words: Int = 15): String {
        val secureRandom = SecureRandom()
        val seedWords = mutableListOf<String>()
        repeat(words) {
            val randomIndex = secureRandom.nextInt(DICTIONARY.size)
            seedWords.add(DICTIONARY[randomIndex])
        }
        return seedWords.joinToString(" ")
    }
}
