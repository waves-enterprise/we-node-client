package com.wavesenterprise.sdk.node.client.grpc.blocking.factory

import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc
import com.wavesenterprise.protobuf.service.messagebroker.BlockchainEventsServiceGrpc
import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.client.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.blocking.event.BlockchainEventsService
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.client.blocking.util.NodeUtilsService
import com.wavesenterprise.sdk.node.client.grpc.blocking.contract.ContractGrpcBlockingService
import com.wavesenterprise.sdk.node.client.grpc.blocking.event.BlockchainEventsGrpcBlockingService
import com.wavesenterprise.sdk.node.client.grpc.blocking.tx.TxGrpcBlockingService
import io.grpc.Channel
import io.grpc.ClientInterceptor

class GrpcNodeServiceFactory(
    private val clientInterceptors: List<ClientInterceptor>,
    private val channel: Channel,
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
            contractServiceStub = ContractServiceGrpc
                .newBlockingStub(channel).withInterceptors(*clientInterceptors.toTypedArray()),
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
        TODO("Not yet implemented")
    }

    override fun blockchainEventsService(): BlockchainEventsService =
        BlockchainEventsGrpcBlockingService(
            channel = channel,
            grpc = BlockchainEventsServiceGrpc.newBlockingStub(channel),
        )

    override fun nodeUtilsService(): NodeUtilsService {
        TODO("Not yet implemented")
    }
}
