package com.wavesenterprise.sdk.tx.signer.node

import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.sign.CreateContractSignRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.address
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.createContractSignRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.createContractTx
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.password
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class TxServiceTxSignerTest {

    lateinit var txServiceTxSigner: TxServiceTxSigner

    @MockK
    lateinit var signCredentialsService: SignCredentialsService

    @MockK
    lateinit var txService: TxService

    @BeforeEach
    fun initMocks() {
        every { signCredentialsService.senderAddress() } returns senderAddress
        every { signCredentialsService.password() } returns password

        txServiceTxSigner = TxServiceTxSigner(
            txService = txService,
            signCredentialsService = signCredentialsService,
        )
    }

    @Test
    fun `should set client credentials in sign request`() {
        val signRequestCaptor = slot<CreateContractSignRequest>()
        every { txService.sign(capture(signRequestCaptor)) } returns createContractTx()

        txServiceTxSigner.sign(createContractSignRequest())

        signRequestCaptor.captured.apply {
            assertEquals(senderAddress, this.senderAddress)
            assertEquals(password, this.password)
        }
    }

    companion object {
        val senderAddress = address()
        val password = password()
    }
}
