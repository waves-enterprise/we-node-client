package com.wavesenterprise.sdk.node.client.feign.contract

import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeyRequest
import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.specific.ContractNotFoundException
import com.wavesenterprise.sdk.node.exception.specific.DataKeyNotExistException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeignContractServiceTest {

    private val weContractServiceApiFeign: WeContractServiceApiFeign = mockk()

    private lateinit var feignContractService: FeignContractService

    private lateinit var contractKeyRequest: ContractKeyRequest

    private lateinit var contractId: ContractId

    @BeforeEach
    fun init() {
        feignContractService = FeignContractService(weContractServiceApiFeign)
        contractId = ContractId(TxId.fromBase58("C2HM9q3QzGSBydnCA4GMcf3cFnTaSuwaWXVtsCSTSmZW"))
        contractKeyRequest = ContractKeyRequest(
            contractId = contractId,
            key = "KEY",
        )
    }

    @Test
    fun `should return null when catch DataKeyNotExistsException`() {
        every {
            weContractServiceApiFeign.contractKey(
                contractId = contractKeyRequest.contractId.asBase58String(),
                key = contractKeyRequest.key,
            )
        } throws DataKeyNotExistException(
            nodeError = NodeError(error = 304, message = "no data for this key"),
            cause = Exception(),
        )
        val dataEntry = feignContractService.getContractKey(contractKeyRequest)
        assertFalse(dataEntry.isPresent)
    }

    @Test
    fun `should return null when catch ContractNotFoundException`() {
        every {
            weContractServiceApiFeign.contractInfo(
                contractId = contractId.asBase58String(),
            )
        } throws ContractNotFoundException(
            nodeError = NodeError(
                error = 600,
                message = "Contract '$contractId' is not found",
            ),
            cause = Exception(),
        )
        val contractInfo = feignContractService.getContractInfo(contractId)
        assertFalse(contractInfo.isPresent)
    }
}
