package com.wavesenterprise.sdk.tx.signer.node

import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.sign.CreateContractSignRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.address
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.createContractSignRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.createContractTx
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.password
import com.wavesenterprise.sdk.tx.signer.node.credentials.Credentials
import com.wavesenterprise.sdk.tx.signer.node.credentials.SignCredentialsProvider
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class TxServiceTxSignerTest {

    lateinit var txServiceTxSigner: TxServiceTxSigner

    @MockK
    lateinit var signCredentialsProvider: SignCredentialsProvider

    @MockK
    lateinit var txService: TxService

    @BeforeEach
    fun initMocks() {
        txServiceTxSigner = TxServiceTxSigner(
            txService = txService,
            signCredentialsProvider = signCredentialsProvider,
        )
    }

    @Test
    fun `should set client credentials in sign request`() {
        every { signCredentialsProvider.credentials() } returns credentials
        val signRequestCaptor = slot<CreateContractSignRequest>()
        every { txService.sign(capture(signRequestCaptor)) } returns createContractTx()

        txServiceTxSigner.sign(createContractSignRequest())

        signRequestCaptor.captured.apply {
            assertEquals(senderAddress, this.senderAddress)
            assertEquals(password, this.password)
        }
    }

    @Test
    fun `should throw exception when senderAddress is EMPTY`() {
        every { signCredentialsProvider.credentials() } returns Credentials(Address.EMPTY, password)
        val signRequestCaptor = slot<CreateContractSignRequest>()
        every { txService.sign(capture(signRequestCaptor)) } returns createContractTx()

        assertThrows<IllegalArgumentException> {
            txServiceTxSigner.sign(createContractSignRequest(senderAddress = Address.EMPTY))
        }.apply {
            assertEquals(
                "Sender address can not be empty [senderAddress = `${Address.EMPTY}`",
                this.message,
            )
        }
    }

    companion object {
        private val senderAddress = address()
        private val password = password()

        val credentials = Credentials(senderAddress, password)
    }
}
