package com.wavesenterprise.sdk.node.client.grpc.blocking.factory

import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc
import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.client.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.client.grpc.blocking.contract.ContractGrpcBlockingService
import com.wavesenterprise.sdk.node.client.grpc.blocking.tx.TxGrpcBlockingService
import io.grpc.ClientInterceptor
import io.grpc.ManagedChannel

class GrpcNodeServiceFactory(
    private val channel: ManagedChannel,
    private val clientInterceptors: List<ClientInterceptor>,
) : NodeBlockingServiceFactory {

    override fun txService(): TxService {
        return TxGrpcBlockingService(
            channel = channel,
        )
    }

    override fun contractService(): ContractService {
        return ContractGrpcBlockingService(
            channel = channel,
            clientInterceptors = clientInterceptors,
            contractServiceStub = ContractServiceGrpc.newBlockingStub(channel).withInterceptors(*clientInterceptors.toTypedArray()),
        )
    }

    override fun addressService(): AddressService {
        TODO("Not yet implemented")
    }

    override fun nodeInfoService(): NodeInfoService {
        TODO("Not yet implemented")
    }

    override fun privacyService(): PrivacyService {
        TODO("Not yet implemented")
    }

    override fun blocksService(): BlocksService {
        throw IllegalArgumentException("Not implemented in grpc")
    }
}
