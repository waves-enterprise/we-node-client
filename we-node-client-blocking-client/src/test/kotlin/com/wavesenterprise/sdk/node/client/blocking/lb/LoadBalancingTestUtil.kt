package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.blocking.credentials.DefaultNodeCredentialsProvider
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.client.blocking.policyDataHashTx
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataSize
import com.wavesenterprise.sdk.node.domain.FileName
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxCount
import com.wavesenterprise.sdk.node.domain.privacy.Data
import com.wavesenterprise.sdk.node.domain.privacy.DataAuthor
import com.wavesenterprise.sdk.node.domain.privacy.DataComment
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemFileInfo
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.tx.UtxSize
import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.NodeServiceUnavailableException
import com.wavesenterprise.sdk.node.exception.specific.ContractNotFoundException
import io.mockk.every
import io.mockk.mockk
import java.util.Optional

private val policies = mapOf(
    "AB" to listOf(Address.fromBase58("A"), Address.fromBase58("B")),
    "ABC" to listOf(Address.fromBase58("A"), Address.fromBase58("B"), Address.fromBase58("C")),
)

fun mockkNodeBlockingServiceFactory(
    addresses: List<String> = listOf(),
    nodeInfoService: NodeInfoService = mockkNodeInfoService(),
    addressService: AddressService = mockkAddressService(),
    privacyService: PrivacyService = mockkPrivacyService(),
    contractService: ContractService = mockkContractService(),
    txService: TxService = mockkTxService(),
): NodeBlockingServiceFactory {
    val nodeBlockingServiceFactory: NodeBlockingServiceFactory = mockk()
    every {
        nodeBlockingServiceFactory.nodeInfoService()
    } returns nodeInfoService
    every {
        nodeBlockingServiceFactory.addressService()
    } returns addressService
    every {
        nodeBlockingServiceFactory.privacyService()
    } returns privacyService.also {
        every {
            it.recipients(any())
        } returns policies["AB"]!!
    }
    every {
        nodeBlockingServiceFactory.contractService()
    } returns contractService
    every {
        nodeBlockingServiceFactory.txService()
    } returns txService
    return nodeBlockingServiceFactory
}

fun mockkTxService(): TxService {
    val txService: TxService = mockk()
    every {
        txService.utxSize()
    } returns UtxSize(
        txCount = TxCount(1),
        size = DataSize(1),
    )
    return txService
}

fun mockkContractService(): ContractService {
    val contractService: ContractService = mockk()
    every {
        contractService.getContractKey(any())
    } returns Optional.empty()
    return contractService
}

fun mockkAddressService(): AddressService {
    val addressService: AddressService = mockk()
    every {
        addressService.getAddresses()
    } returns listOf()
    return addressService
}

fun mockkNodeInfoService(): NodeInfoService {
    val nodeInfoService: NodeInfoService = mockk()
    every {
        nodeInfoService.getNodeOwner()
    } returns NodeOwner(Address.fromBase58("B"), PublicKey.fromBase58(""))
    return nodeInfoService
}

fun mockkPrivacyService(): PrivacyService {
    val privacyService: PrivacyService = mockk()
    every {
        privacyService.data(any())
    } returns Optional.of(Data("".toByteArray()))
    every {
        privacyService.info(any())
    } returns Optional.of(
        PolicyItemInfoResponse(
            senderAddress = Address.fromBase58(""),
            policyId = PolicyId.fromBase58(""),
            info = PolicyItemFileInfo(
                filename = FileName(""),
                size = DataSize(1),
                timestamp = Timestamp(1),
                author = DataAuthor(""),
                comment = DataComment(""),
            ),
            dataHash = Hash("".toByteArray()),
        ),
    )
    every {
        privacyService.sendData(any())
    } returns policyDataHashTx()
    return privacyService
}

fun nodeNotAvailableFailingMockClient(addresses: List<String> = listOf()): NodeBlockingServiceFactory {
    val nodeBlockingServiceFactory: NodeBlockingServiceFactory = mockk()
    every {
        nodeBlockingServiceFactory.contractService()
    } throws NodeServiceUnavailableException(cause = Exception())
    every {
        nodeBlockingServiceFactory.addressService()
    } returns mockkAddressService()
    every {
        nodeBlockingServiceFactory.nodeInfoService()
    } returns mockkNodeInfoService()
    every {
        nodeBlockingServiceFactory.privacyService()
    } throws NodeServiceUnavailableException(cause = Exception())
    every {
        nodeBlockingServiceFactory.contractService().getContractKey(any())
    } throws NodeServiceUnavailableException(cause = Exception())
    every {
        nodeBlockingServiceFactory.txService()
    } throws NodeServiceUnavailableException(cause = Exception())
    return nodeBlockingServiceFactory
}

fun retryableFailingMockClient(): NodeBlockingServiceFactory {
    val nodeBlockingServiceFactory: NodeBlockingServiceFactory = mockk()
    every {
        nodeBlockingServiceFactory.addressService()
    } returns mockkAddressService()
    every {
        nodeBlockingServiceFactory.nodeInfoService()
    } returns mockkNodeInfoService()
    every {
        nodeBlockingServiceFactory.contractService()
    } throws NodeServiceUnavailableException(Exception())
    return nodeBlockingServiceFactory
}

fun businessFailingMockClient(): NodeBlockingServiceFactory {
    val nodeBlockingServiceFactory: NodeBlockingServiceFactory = mockk()
    every {
        nodeBlockingServiceFactory.addressService()
    } returns mockkAddressService()
    every {
        nodeBlockingServiceFactory.nodeInfoService()
    } returns mockkNodeInfoService()
    every {
        nodeBlockingServiceFactory.contractService()
    } throws ContractNotFoundException(
        nodeError = NodeError(
            error = 600,
            message = "Contract is not found",
        ),
        cause = Exception(),
    )
    return nodeBlockingServiceFactory
}

fun nodeCredentialsProvider(nodeCredentials: Map<Address, Password?> = mapOf()) =
    DefaultNodeCredentialsProvider(nodeCredentials)
