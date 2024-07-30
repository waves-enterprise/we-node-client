package com.wavesenterprise.tx.signer.bouncycastle

import com.wavesenterprise.tx.signer.bouncycastle.gost.BouncyCastleUtil
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BouncyCastleUtilTest {
    @Test
    fun `should generate correct encrypt and decrypt seed phrase`() {
        val encryptedPrivateKey = BouncyCastleUtil.encryptSeedAES(SEED, PASS)
        val decryptedPrivateKey = BouncyCastleUtil.decryptSeedAES(encryptedPrivateKey, PASS)
        assertTrue(SEED == decryptedPrivateKey)
    }

    @Test
    fun `should generate key pair from seed`() {
        val expectedPrivateKeyBytes = byteArrayOf(
            48, 74, 2, 1, 0, 48, 17, 6, 6, 42, -123, 3, 2, 2, 19, 6, 7, 42, -123, 3, 2, 2, 35, 1, 4, 50, 48, 48, 2, 1,
            1, 4, 32, 10, 54, -119, -124, 35, 119, -33, 49, 84, -84, -123, -121, 4, -87, -32, -42, 22, 73, 31, 107,
            -58, -52, 3, 80, -92, 60, 30, -9, -114, 65, 37, 48, -96, 9, 6, 7, 42, -123, 3, 2, 2, 35, 1,
        )
        val expectedPublicKeyBytes = byteArrayOf(
            48, 99, 48, 28, 6, 6, 42, -123, 3, 2, 2, 19, 48, 18, 6, 7, 42, -123, 3, 2, 2, 35, 1, 6, 7, 42, -123, 3, 2,
            2, 30, 1, 3, 67, 0, 4, 64, 59, -97, 8, -69, 19, 111, 46, 105, 35, -33, -4, -90, 107, 58, 43, -121, -85, -14,
            -5, 29, -105, -65, 25, -103, 59, 107, 23, 48, 81, -115, 59, 12, 95, 30, 20, -11, -31, 67, 40, 52, 15, -70,
            15, 48, 75, 114, 84, -2, -30, 114, -115, 62, 74, -11, -92, -91, -119, -82, -95, 53, 69, -108, 121, -99,
        )
        BouncyCastleUtil.generateKeyPairFromSeed(SEED).also {
            assertArrayEquals(expectedPrivateKeyBytes, it.private.encoded)
            assertArrayEquals(expectedPublicKeyBytes, it.public.encoded)
        }
    }

    @Test
    fun `should correct generate seed phrase with size 15`() {
        val size = 15
        BouncyCastleUtil.generateSeed(words = size).apply {
            assertEquals(size, this.split(" ").size)
        }
    }

    companion object {
        const val PASS = "test"
        const val SEED = "blur antenna bridge vessel cliff apology oppose " +
            "knee father royal lend bubble rough aspect hood"
    }
}
