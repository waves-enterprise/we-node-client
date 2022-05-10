package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.http.sign.AtomicSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.BurnSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.CallContractSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.CreateAliasSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.CreateContractSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.CreatePolicySignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.DataSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.DisableContractSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.GenesisPermitSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.IssueSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.LeaseCancelSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.LeaseSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.MassTransferSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.PermitSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.RegisterNodeSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.ReissueSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.SetAssetScriptSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.SetScriptSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.SignRequestDto
import com.wavesplatform.we.sdk.node.client.http.sign.SponsorFeeSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.TransferSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.UpdateContractSignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.sign.UpdatePolicySignRequestDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.AtomicTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.AtomicTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.BurnTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.BurnTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.CallContractTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.CallContractTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.CreateAliasTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.CreateAliasTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.CreateContractTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.CreateContractTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.CreatePolicyTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.CreatePolicyTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.DataTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.DataTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.DisableContractTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.DisableContractTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.ExecutedContractTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.ExecutedContractTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.GenesisPermitTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.GenesisPermitTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.GenesisRegisterNodeTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.GenesisRegisterNodeTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.GenesisTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.GenesisTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.IssueTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.IssueTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.LeaseCancelTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.LeaseCancelTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.LeaseTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.LeaseTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.MassTransferTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.MassTransferTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.PermitTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.PermitTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.PolicyDataHashTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.PolicyDataHashTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.RegisterNodeTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.RegisterNodeTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.ReissueTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.ReissueTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.SetAssetScriptTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.SetAssetScriptTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.SetScriptTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.SetScriptTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.SponsorFeeTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.SponsorFeeTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.TransferTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.TransferTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.TxDto
import com.wavesplatform.we.sdk.node.client.http.tx.UpdateContractTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.UpdateContractTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.UpdatePolicyTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.UpdatePolicyTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.sign.AtomicSignRequest
import com.wavesplatform.we.sdk.node.client.sign.BurnSignRequest
import com.wavesplatform.we.sdk.node.client.sign.CallContractSignRequest
import com.wavesplatform.we.sdk.node.client.sign.CreateAliasSignRequest
import com.wavesplatform.we.sdk.node.client.sign.CreateContractSignRequest
import com.wavesplatform.we.sdk.node.client.sign.CreatePolicySignRequest
import com.wavesplatform.we.sdk.node.client.sign.DataSignRequest
import com.wavesplatform.we.sdk.node.client.sign.DisableContractSignRequest
import com.wavesplatform.we.sdk.node.client.sign.GenesisPermitSignRequest
import com.wavesplatform.we.sdk.node.client.sign.IssueSignRequest
import com.wavesplatform.we.sdk.node.client.sign.LeaseCancelSignRequest
import com.wavesplatform.we.sdk.node.client.sign.LeaseSignRequest
import com.wavesplatform.we.sdk.node.client.sign.MassTransferSignRequest
import com.wavesplatform.we.sdk.node.client.sign.PermitSignRequest
import com.wavesplatform.we.sdk.node.client.sign.RegisterNodeSignRequest
import com.wavesplatform.we.sdk.node.client.sign.ReissueSignRequest
import com.wavesplatform.we.sdk.node.client.sign.SetAssetScriptSignRequest
import com.wavesplatform.we.sdk.node.client.sign.SetScriptSignRequest
import com.wavesplatform.we.sdk.node.client.sign.SignRequest
import com.wavesplatform.we.sdk.node.client.sign.SponsorFeeSignRequest
import com.wavesplatform.we.sdk.node.client.sign.TransferSignRequest
import com.wavesplatform.we.sdk.node.client.sign.UpdateContractSignRequest
import com.wavesplatform.we.sdk.node.client.sign.UpdatePolicySignRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import java.net.URL

class KtorTxService(
    private val nodeUrl: URL,
    private val httpClient: HttpClient,
) : TxService {
    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : Tx> sign(request: SignRequest<T>): T =
        when (request) {
            is AtomicSignRequest -> signDto(request.toDto()).toDomain()
            is BurnSignRequest -> signDto(request.toDto()).toDomain()
            is CallContractSignRequest -> signDto(request.toDto()).toDomain()
            is CreateAliasSignRequest -> signDto(request.toDto()).toDomain()
            is CreateContractSignRequest -> signDto(request.toDto()).toDomain()
            is CreatePolicySignRequest -> signDto(request.toDto()).toDomain()
            is DataSignRequest -> signDto(request.toDto()).toDomain()
            is DisableContractSignRequest -> signDto(request.toDto()).toDomain()
            is GenesisPermitSignRequest -> signDto(request.toDto()).toDomain()
            is IssueSignRequest -> signDto(request.toDto()).toDomain()
            is LeaseCancelSignRequest -> signDto(request.toDto()).toDomain()
            is LeaseSignRequest -> signDto(request.toDto()).toDomain()
            is MassTransferSignRequest -> signDto(request.toDto()).toDomain()
            is PermitSignRequest -> signDto(request.toDto()).toDomain()
            is RegisterNodeSignRequest -> signDto(request.toDto()).toDomain()
            is ReissueSignRequest -> signDto(request.toDto()).toDomain()
            is SetAssetScriptSignRequest -> signDto(request.toDto()).toDomain()
            is SetScriptSignRequest -> signDto(request.toDto()).toDomain()
            is SponsorFeeSignRequest -> signDto(request.toDto()).toDomain()
            is TransferSignRequest -> signDto(request.toDto()).toDomain()
            is UpdateContractSignRequest -> signDto(request.toDto()).toDomain()
            is UpdatePolicySignRequest -> signDto(request.toDto()).toDomain()
        } as T

    private suspend inline fun <reified T : TxDto, reified R : SignRequestDto<T>> signDto(request: R): T =
        httpClient.post(nodeUrl) {
            url.appendPathSegments(TRANSACTIONS, SIGN)
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
            setBody(request)
        }.body()

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : Tx> signAndBroadcast(request: SignRequest<T>): T =
        when (request) {
            is AtomicSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is BurnSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is CallContractSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is CreateAliasSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is CreateContractSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is CreatePolicySignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is DataSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is DisableContractSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is GenesisPermitSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is IssueSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is LeaseCancelSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is LeaseSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is MassTransferSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is PermitSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is RegisterNodeSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is ReissueSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is SetAssetScriptSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is SetScriptSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is SponsorFeeSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is TransferSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is UpdateContractSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is UpdatePolicySignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
        } as T

    private suspend inline fun <reified T : TxDto, reified R : SignRequestDto<T>> signAndBroadcastDto(request: R): T =
        httpClient.post(nodeUrl) {
            url.appendPathSegments(TRANSACTIONS, SIGN_AND_BROADCAST)
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
            setBody(request)
        }.body()

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : Tx> broadcast(tx: T): T =
        when (val tx = tx as Tx) {
            is AtomicTx -> broadcastDto(tx.toDto()).toDomain()
            is BurnTx -> broadcastDto(tx.toDto()).toDomain()
            is CallContractTx -> broadcastDto(tx.toDto()).toDomain()
            is CreateAliasTx -> broadcastDto(tx.toDto()).toDomain()
            is CreateContractTx -> broadcastDto(tx.toDto()).toDomain()
            is CreatePolicyTx -> broadcastDto(tx.toDto()).toDomain()
            is DataTx -> broadcastDto(tx.toDto()).toDomain()
            is DisableContractTx -> broadcastDto(tx.toDto()).toDomain()
            is ExecutedContractTx -> broadcastDto(tx.toDto()).toDomain()
            is GenesisPermissionTx -> broadcastDto(tx.toDto()).toDomain()
            is GenesisRegisterNodeTx -> broadcastDto(tx.toDto()).toDomain()
            is GenesisTx -> broadcastDto(tx.toDto()).toDomain()
            is IssueTx -> broadcastDto(tx.toDto()).toDomain()
            is LeaseCancelTx -> broadcastDto(tx.toDto()).toDomain()
            is LeaseTx -> broadcastDto(tx.toDto()).toDomain()
            is MassTransferTx -> broadcastDto(tx.toDto()).toDomain()
            is PermitTx -> broadcastDto(tx.toDto()).toDomain()
            is PolicyDataHashTx -> broadcastDto(tx.toDto()).toDomain()
            is RegisterNodeTx -> broadcastDto(tx.toDto()).toDomain()
            is ReissueTx -> broadcastDto(tx.toDto()).toDomain()
            is SetAssetScriptTx -> broadcastDto(tx.toDto()).toDomain()
            is SetScriptTx -> broadcastDto(tx.toDto()).toDomain()
            is SponsorFeeTx -> broadcastDto(tx.toDto()).toDomain()
            is TransferTx -> broadcastDto(tx.toDto()).toDomain()
            is UpdateContractTx -> broadcastDto(tx.toDto()).toDomain()
            is UpdatePolicyTx -> broadcastDto(tx.toDto()).toDomain()
        } as T

    private suspend inline fun <reified T : TxDto> broadcastDto(tx: T): T =
        httpClient.post(nodeUrl) {
            url.appendPathSegments(TRANSACTIONS, BROADCAST)
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
            setBody(tx)
        }.body()

    companion object {
        const val TRANSACTIONS = "transactions"
        const val SIGN = "sign"
        const val BROADCAST = "broadcast"
        const val SIGN_AND_BROADCAST = "signAndBroadcast"
    }
}
