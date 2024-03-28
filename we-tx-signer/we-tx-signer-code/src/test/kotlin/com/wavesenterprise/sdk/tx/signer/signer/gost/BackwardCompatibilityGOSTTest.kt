package com.wavesenterprise.sdk.tx.signer.signer.gost

import com.wavesenterprise.sdk.node.domain.Address.Companion.address
import com.wavesenterprise.sdk.tx.signer.signer.gost.util.BouncyCastleUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class BackwardCompatibilityGOSTTest {

    @ParameterizedTest
    @MethodSource("wallets")
    fun `should compare addresses and public keys`(
        wallet: Triple<String, String, String>
    ) {
        val (address, publicKey, encryptedPrivateKey) = wallet
        val seed = BouncyCastleUtil.decryptSeedAES(encryptedPrivateKey, password)
        val kp = BouncyCastleUtil.generateKeyPairFromSeed(seed)
        val signer = BouncyCastleSigner(kp.private, 'I'.toByte())
        val publicKeyFromSigner = signer.getPublicKey()

        assertEquals(publicKey, publicKeyFromSigner.asBase58String())
        assertEquals(address, signer.createAddress(publicKeyFromSigner.bytes).address.asBase58String())
    }

    companion object {
        const val password = "test"
        @JvmStatic
        private fun wallets(): Stream<Arguments> =
            setOf(
                Triple(
                    "3HgmGqvKGSjrTzEy6wEWULFihXBxJR2z9kA",
                    "2C8xGYVY9se7ZZjpG1fxhPFt3BBXf1V3ChiWkNMe1MfRVjhCgmEHRNBLxWtqpVGmRZh995iLiR33hS5n4qTJAQrG",
                    "U2FsdGVkX1+65pEFu0GVfoxkxxn1/3gEbQDl4FF8Gw8gFU/rFyANkzv9DcdplwXds7TfP8KpT9oAo/BrbQHp9JG8I" +
                        "ts4dHrpTuifBA02gtwd9+Kfv3wKR0raM0q46Yt40bLi6iwh2sY/xVM6xsmJPg=="
                ),
                Triple(
                    "3HgJnaYScHoLTHK5FSYPkz1wMznQ9DP4T8V",
                    "3NkKZLY4fMmHk1Zksake7FzejwPTLFvJeMeExP1YiCxwrUoDR71Y1qQS6QbcJPfRjpvGMigaMNDjmdx9hJgiga6L",
                    "U2FsdGVkX1+aUSnkM/7TV7n1aLJ5bTdbTTeY87BYczjGHlZ6n839xU57Lu8xuHTG7Y2Y9lGvs4pBZB9huCsD" +
                        "thS6uZ2hXwOUmBWNwMDVhA485QqsusPN7ANyHZ78plJ6B7CcLwQHamtgmsYBtOzAcw=="
                )
            ).map { Arguments.of(it) }.stream()
    }
}
