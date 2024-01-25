package com.wavesenterprise.sdk.tx.signer.signer.gost

import com.wavesenterprise.sdk.tx.signer.signer.gost.util.CryptoProUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import ru.CryptoPro.JCSP.JCSP
import java.security.Security

class CryptoProUtilTest {

    @BeforeEach
    fun init() {
        Security.addProvider(JCSP())
    }

    @Test
    fun `should generate and store crypto pro key pair and delete entry`() {
        val password = "password"
        val alias = "alias"
        CryptoProUtil.generateAndStoreKeyPair(password, alias)

        val keyPair = CryptoProUtil.getKeyPair(password, alias)
        val publicKeyFromContainer = CryptoProUtil.getPublicKey(alias)

        assertEquals(keyPair.public, publicKeyFromContainer)
        assertDoesNotThrow {
            CryptoProUtil.getPrivateKey(password, alias)
        }

        CryptoProUtil.deleteEntry(alias)

        assertThrows<Exception> {
            CryptoProUtil.getKeyPair(password, alias)
        }
    }
}
