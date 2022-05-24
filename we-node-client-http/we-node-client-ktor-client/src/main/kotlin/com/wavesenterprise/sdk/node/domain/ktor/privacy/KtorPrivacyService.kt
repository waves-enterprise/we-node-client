package com.wavesenterprise.sdk.node.domain.ktor.privacy

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.coroutines.privacy.PrivacyService
import com.wavesenterprise.sdk.node.domain.http.privacy.PolicyItemInfoResponseDto
import com.wavesenterprise.sdk.node.domain.http.privacy.PolicyItemInfoResponseDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.http.privacy.SendDataRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.tx.PolicyDataHashTxDto
import com.wavesenterprise.sdk.node.domain.http.tx.PolicyDataHashTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.ktor.privacy.KtorPrivacyService.Companion.Privacy.BROADCAST
import com.wavesenterprise.sdk.node.domain.privacy.Data
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import java.net.URL

class KtorPrivacyService(
    private val nodeUrl: URL,
    private val httpClient: HttpClient,
) : PrivacyService {
    override suspend fun sendData(request: SendDataRequest): PolicyDataHashTx =
        httpClient.post(nodeUrl) {
            url.appendPathSegments(Privacy.PATH, Privacy.SEND_DATA)
            parameter(BROADCAST, request.broadcastTx)
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
            setBody(request.toDto())
        }.body<PolicyDataHashTxDto>().toDomain()

    override suspend fun info(request: PolicyItemRequest): PolicyItemInfoResponse =
        httpClient.get(nodeUrl) {
            url.appendPathSegments(
                Privacy.PATH,
                request.policyId.asBase58String(),
                Privacy.Policy.GET_INFO,
                request.dataHash.asHexString(),
            )
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
        }.body<PolicyItemInfoResponseDto>().toDomain()

    override suspend fun data(request: PolicyItemRequest): Data =
        httpClient.get(nodeUrl) {
            url.appendPathSegments(
                Privacy.PATH,
                request.policyId.asBase58String(),
                Privacy.Policy.GET_DATA,
                request.dataHash.asHexString(),
            )
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
        }.body<String>().let { Data.fromBase64(it) }

    override suspend fun exists(request: PolicyItemRequest): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun recipients(policyId: PolicyId): List<Address> =
        httpClient.get(nodeUrl) {
            url.appendPathSegments(
                Privacy.PATH,
                policyId.asBase58String(),
                Privacy.Policy.RECIPIENTS,
            )
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
        }.body<List<String>>().map { Address.fromBase58(it) }

    override suspend fun owners(policyId: PolicyId): List<Address> =
        httpClient.get(nodeUrl) {
            url.appendPathSegments(
                Privacy.PATH,
                policyId.asBase58String(),
                Privacy.Policy.OWNERS,
            )
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
        }.body<List<String>>().map { Address.fromBase58(it) }

    override suspend fun hashes(policyId: PolicyId): List<Hash> =
        httpClient.get(nodeUrl) {
            url.appendPathSegments(
                Privacy.PATH,
                policyId.asBase58String(),
                Privacy.Policy.HASHES,
            )
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
        }.body<List<String>>().map { Hash.fromHexString(it) }

    companion object {
        object Privacy {
            const val PATH = "privacy"
            const val SEND_DATA = "sendData"
            const val BROADCAST = "broadcast"
            object Policy {
                const val GET_DATA = "getData"
                const val GET_INFO = "getInfo"
                const val RECIPIENTS = "recipients"
                const val HASHES = "hashes"
                const val OWNERS = "owners"
            }
        }
    }
}
