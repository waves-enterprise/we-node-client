package com.wavesenterprise.sdk.node.domain.grpc.blocking.factory

import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc
import com.wavesenterprise.sdk.node.domain.blocking.address.AddressService
import com.wavesenterprise.sdk.node.domain.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.domain.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.grpc.blocking.contract.ContractGrpcBlockingService
import com.wavesenterprise.sdk.node.domain.grpc.blocking.tx.TxGrpcBlockingService
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
}
