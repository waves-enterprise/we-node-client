package com.wavesenterprise.sdk.node.client.grpc.blocking.privacy

import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.privacy.Data
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import java.util.Optional

class PrivacyGrpcBlockingService : PrivacyService {
    override fun sendData(request: SendDataRequest): PolicyDataHashTx {
        TODO("Not yet implemented")
    }

    override fun info(request: PolicyItemRequest): Optional<PolicyItemInfoResponse> {
        TODO("Not yet implemented")
    }

    override fun data(request: PolicyItemRequest): Optional<Data> {
        TODO("Not yet implemented")
    }

    override fun exists(request: PolicyItemRequest): Boolean {
        TODO("Not yet implemented")
    }

    override fun recipients(policyId: PolicyId): List<Address> {
        TODO("Not yet implemented")
    }

    override fun owners(policyId: PolicyId): List<Address> {
        TODO("Not yet implemented")
    }

    override fun hashes(policyId: PolicyId): List<Hash> {
        TODO("Not yet implemented")
    }
}
