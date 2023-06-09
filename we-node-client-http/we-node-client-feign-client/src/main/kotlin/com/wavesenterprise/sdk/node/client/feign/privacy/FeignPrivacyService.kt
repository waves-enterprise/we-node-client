package com.wavesenterprise.sdk.node.client.feign.privacy

import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.http.privacy.PolicyItemInfoResponseDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.privacy.SendDataRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.PolicyDataHashTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.privacy.Data
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.sdk.node.exception.specific.PolicyDoesNotExistException
import com.wavesenterprise.sdk.node.exception.specific.PolicyItemDataIsMissingException
import java.util.Optional

class FeignPrivacyService(
    private val wePrivacyServiceApiFeign: WePrivacyServiceApiFeign,
) : PrivacyService {
    override fun sendData(request: SendDataRequest): PolicyDataHashTx =
        wePrivacyServiceApiFeign.sendDataToPrivacy(request.toDto()).toDomain()

    override fun info(request: PolicyItemRequest): Optional<PolicyItemInfoResponse> =
        try {
            Optional.of(
                wePrivacyServiceApiFeign.getPolicyItemInfo(
                    policyId = request.policyId.asBase58String(),
                    policyItemHash = request.dataHash.asBase58String(),
                ).toDomain()
            )
        } catch (ex: PolicyItemDataIsMissingException) {
            Optional.empty()
        }

    override fun data(request: PolicyItemRequest): Optional<Data> =
        try {
            Optional.of(
                Data.fromByteArray(
                    bytes = wePrivacyServiceApiFeign.getDataFromPrivacy(
                        policyId = request.policyId.asBase58String(),
                        policyItemHash = request.dataHash.asBase58String(),
                    )
                )
            )
        } catch (ex: PolicyItemDataIsMissingException) {
            Optional.empty<Data>()
        } catch (ex: PolicyDoesNotExistException) {
            Optional.empty<Data>()
        }

    override fun exists(request: PolicyItemRequest): Boolean =
        wePrivacyServiceApiFeign.getDataFromPrivacy(
            policyId = request.policyId.asBase58String(),
            policyItemHash = request.dataHash.asBase58String(),
        ).isNotEmpty()

    override fun recipients(policyId: PolicyId): List<Address> =
        wePrivacyServiceApiFeign.getPolicyRecipients(policyId.asBase58String()).map { Address.fromBase58(it) }

    override fun owners(policyId: PolicyId): List<Address> =
        wePrivacyServiceApiFeign.getPolicyOwners(policyId = policyId.asBase58String()).map { Address.fromBase58(it) }

    override fun hashes(policyId: PolicyId): List<Hash> =
        wePrivacyServiceApiFeign.getPolicyHashes(policyId = policyId.asBase58String()).map { Hash.fromStringBase58(it) }
}
