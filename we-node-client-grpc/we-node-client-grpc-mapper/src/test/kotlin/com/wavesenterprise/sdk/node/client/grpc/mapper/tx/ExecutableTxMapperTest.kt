package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.ExecutableTxMapper.domain
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx
import com.wavesenterprise.transaction.protobuf.docker.CallContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.CreateContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.UpdateContractTransaction
import com.wavesenterprise.transaction.protobuf.executableTransaction
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExecutableTxMapperTest {

    @BeforeEach
    fun beforeEach() {
        clearAllMocks()
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `should map to CreateContractTx`() {
        val (grpcCreateContractTx, domainCreateContractTx) =
            mockk<CreateContractTransaction>() to mockk<CreateContractTx>()
        val executableTxVersion = 4
        val grpcTx = executableTransaction {
            createContractTransaction = grpcCreateContractTx
            version = executableTxVersion
        }
        mockkStatic(CreateContractTxMapper::domainInternal)
        every { CreateContractTxMapper.domainInternal(any(), any()) } returns domainCreateContractTx

        grpcTx
            .domain()
            .shouldBe(domainCreateContractTx)

        verify {
            CreateContractTxMapper.domainInternal(
                tx = grpcCreateContractTx,
                version = TxVersion(executableTxVersion),
            )
        }
    }

    @Test
    fun `should map to CallContractTx`() {
        val (grpcCallContractTx, domainCallContractTx) =
            mockk<CallContractTransaction>() to mockk<CallContractTx>()
        val executableTxVersion = 4
        val grpcTx = executableTransaction {
            callContractTransaction = grpcCallContractTx
            version = executableTxVersion
        }
        mockkStatic(CallContractTxMapper::domainInternal)
        every { CallContractTxMapper.domainInternal(any(), any()) } returns domainCallContractTx

        grpcTx
            .domain()
            .shouldBe(domainCallContractTx)

        verify {
            CallContractTxMapper.domainInternal(
                tx = grpcCallContractTx,
                version = TxVersion(executableTxVersion),
            )
        }
    }

    @Test
    fun `should map to UpdateContractTx`() {
        val (grpcUpdateContractTx, domainUpdateContractTx) =
            mockk<UpdateContractTransaction>() to mockk<UpdateContractTx>()
        val executableTxVersion = 4
        val grpcTx = executableTransaction {
            updateContractTransaction = grpcUpdateContractTx
            version = executableTxVersion
        }
        mockkStatic(UpdateContractTxMapper::domainInternal)
        every { UpdateContractTxMapper.domainInternal(any(), any()) } returns domainUpdateContractTx

        grpcTx
            .domain()
            .shouldBe(domainUpdateContractTx)

        verify {
            UpdateContractTxMapper.domainInternal(
                tx = grpcUpdateContractTx,
                version = TxVersion(executableTxVersion),
            )
        }
    }

    @Test
    fun `should throw IllegalStateException`() {
        val grpcTx = executableTransaction {}

        val ex = assertThrows<IllegalStateException> {
            grpcTx.domain()
        }
        ex.message shouldBe "Transaction not set"
    }
}
