package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.transaction.protobuf.acl.permitTransaction
import com.wavesenterprise.transaction.protobuf.atomicInnerTransaction
import com.wavesenterprise.transaction.protobuf.createPolicyTransaction
import com.wavesenterprise.transaction.protobuf.docker.callContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.createContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.disableContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.executedContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.updateContractTransaction
import com.wavesenterprise.transaction.protobuf.policyDataHashTransaction
import com.wavesenterprise.transaction.protobuf.transfer.transferTransaction
import com.wavesenterprise.transaction.protobuf.updatePolicyTransaction
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AtomicInnerTxMapperTest {

    @BeforeEach
    fun beforeEach() {
        clearAllMocks()
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    private val mockkVersion = 1

    @Test
    fun `should call map to CallContractTx`() {
        mockkStatic(CallContractTxMapper::domainInternal)
        every { CallContractTxMapper.domainInternal(any(), any()) } returns mockk()
        val innerTransaction = callContractTransaction { }
        val mockkGrpcTransaction = atomicInnerTransaction {
            callContractTransaction = innerTransaction
        }
        AtomicInnerTxMapper.domainInternal(mockkGrpcTransaction, TxVersion(mockkVersion))
        verify { CallContractTxMapper.domainInternal(innerTransaction, TxVersion(mockkVersion)) }
    }

    @Test
    fun `should call map to CreateContractTx`() {
        mockkStatic(CreateContractTxMapper::domainInternal)
        every { CreateContractTxMapper.domainInternal(any(), any()) } returns mockk()
        val innerTransaction = createContractTransaction { }
        val mockkGrpcTransaction = atomicInnerTransaction {
            createContractTransaction = innerTransaction
        }
        AtomicInnerTxMapper.domainInternal(mockkGrpcTransaction, TxVersion(mockkVersion))
        verify { CreateContractTxMapper.domainInternal(innerTransaction, TxVersion(mockkVersion)) }
    }

    @Test
    fun `should call map to DisableContractTx`() {
        mockkStatic(DisableContractTxMapper::domainInternal)
        every { DisableContractTxMapper.domainInternal(any(), any()) } returns mockk()
        val innerTransaction = disableContractTransaction { }
        val mockkGrpcTransaction = atomicInnerTransaction {
            disableContractTransaction = innerTransaction
        }
        AtomicInnerTxMapper.domainInternal(mockkGrpcTransaction, TxVersion(mockkVersion))
        verify { DisableContractTxMapper.domainInternal(innerTransaction, TxVersion(mockkVersion)) }
    }

    @Test
    fun `should call map to UpdateContractTx`() {
        mockkStatic(UpdateContractTxMapper::domainInternal)
        every { UpdateContractTxMapper.domainInternal(any(), any()) } returns mockk()
        val innerTransaction = updateContractTransaction { }
        val mockkGrpcTransaction = atomicInnerTransaction {
            updateContractTransaction = innerTransaction
        }
        AtomicInnerTxMapper.domainInternal(mockkGrpcTransaction, TxVersion(mockkVersion))
        verify { UpdateContractTxMapper.domainInternal(innerTransaction, TxVersion(mockkVersion)) }
    }

    @Test
    fun `should call map to ExecutedContractTx`() {
        mockkStatic(ExecutedContractTxMapper::domainInternal)
        every { ExecutedContractTxMapper.domainInternal(any(), any()) } returns mockk()
        val innerTransaction = executedContractTransaction { }
        val mockkGrpcTransaction = atomicInnerTransaction {
            executedContractTransaction = innerTransaction
        }
        AtomicInnerTxMapper.domainInternal(mockkGrpcTransaction, TxVersion(mockkVersion))
        verify { ExecutedContractTxMapper.domainInternal(innerTransaction, TxVersion(mockkVersion)) }
    }

    @Test
    fun `should call map to CreatePolicyTx`() {
        mockkStatic(CreatePolicyTxMapper::domainInternal)
        every { CreatePolicyTxMapper.domainInternal(any(), any()) } returns mockk()
        val innerTransaction = createPolicyTransaction { }
        val mockkGrpcTransaction = atomicInnerTransaction {
            createPolicyTransaction = innerTransaction
        }
        AtomicInnerTxMapper.domainInternal(mockkGrpcTransaction, TxVersion(mockkVersion))
        verify { CreatePolicyTxMapper.domainInternal(innerTransaction, TxVersion(mockkVersion)) }
    }

    @Test
    fun `should call map to PolicyDataHashTx`() {
        mockkStatic(PolicyDataHashTxMapper::domainInternal)
        every { PolicyDataHashTxMapper.domainInternal(any(), any()) } returns mockk()
        val innerTransaction = policyDataHashTransaction { }
        val mockkGrpcTransaction = atomicInnerTransaction {
            policyDataHashTransaction = innerTransaction
        }
        AtomicInnerTxMapper.domainInternal(mockkGrpcTransaction, TxVersion(mockkVersion))
        verify { PolicyDataHashTxMapper.domainInternal(innerTransaction, TxVersion(mockkVersion)) }
    }

    @Test
    fun `should call map to UpdatePolicyTx`() {
        mockkStatic(UpdatePolicyTxMapper::domainInternal)
        every { UpdatePolicyTxMapper.domainInternal(any(), any()) } returns mockk()
        val innerTransaction = updatePolicyTransaction { }
        val mockkGrpcTransaction = atomicInnerTransaction {
            updatePolicyTransaction = innerTransaction
        }
        AtomicInnerTxMapper.domainInternal(mockkGrpcTransaction, TxVersion(mockkVersion))
        verify { UpdatePolicyTxMapper.domainInternal(innerTransaction, TxVersion(mockkVersion)) }
    }

    @Test
    fun `should call map to PermitTx`() {
        mockkStatic(PermitTxMapper::domainInternal)
        every { PermitTxMapper.domainInternal(any(), any()) } returns mockk()
        val innerTransaction = permitTransaction { }
        val mockkGrpcTransaction = atomicInnerTransaction {
            permitTransaction = innerTransaction
        }
        AtomicInnerTxMapper.domainInternal(mockkGrpcTransaction, TxVersion(mockkVersion))
        verify { PermitTxMapper.domainInternal(innerTransaction, TxVersion(mockkVersion)) }
    }

    @Test
    fun `should call map to TransferTx`() {
        mockkStatic(TransferTxMapper::domainInternal)
        every { TransferTxMapper.domainInternal(any(), any()) } returns mockk()
        val innerTransaction = transferTransaction { }
        val mockkGrpcTransaction = atomicInnerTransaction {
            transferTransaction = innerTransaction
        }
        AtomicInnerTxMapper.domainInternal(mockkGrpcTransaction, TxVersion(mockkVersion))
        verify { TransferTxMapper.domainInternal(innerTransaction, TxVersion(mockkVersion)) }
    }
}
