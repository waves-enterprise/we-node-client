package com.wavesenterprise.sdk.node.client.ktor.tx

import com.wavesenterprise.sdk.node.client.coroutines.tx.TxService
import com.wavesenterprise.sdk.node.client.http.sign.AtomicSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.BurnSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.CallContractSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.CreateAliasSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.CreateContractSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.CreatePolicySignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.DataSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.DisableContractSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.GenesisPermitSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.IssueSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.LeaseCancelSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.LeaseSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.MassTransferSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.PermitSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.RegisterNodeSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.ReissueSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.SetAssetScriptSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.SetScriptSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.SignRequestDto
import com.wavesenterprise.sdk.node.client.http.sign.SponsorFeeSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.TransferSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.UpdateContractSignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.sign.UpdatePolicySignRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.AtomicTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.AtomicTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.BurnTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.BurnTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.CallContractTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.CallContractTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.CreateAliasTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.CreateAliasTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.CreateContractTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.CreateContractTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.CreatePolicyTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.CreatePolicyTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.DataTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.DataTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.DisableContractTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.DisableContractTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.ExecutedContractTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.ExecutedContractTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.GenesisPermitTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.GenesisPermitTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.GenesisRegisterNodeTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.GenesisRegisterNodeTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.GenesisTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.GenesisTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.IssueTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.IssueTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.LeaseCancelTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.LeaseCancelTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.LeaseTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.LeaseTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.MassTransferTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.MassTransferTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.PermitTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.PermitTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.PolicyDataHashTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.PolicyDataHashTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.RegisterNodeTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.RegisterNodeTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.ReissueTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.ReissueTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.SetAssetScriptTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.SetAssetScriptTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.SetScriptTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.SetScriptTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.SponsorFeeTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.SponsorFeeTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.TransferTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.TransferTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.TxDto
import com.wavesenterprise.sdk.node.client.http.tx.TxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.UpdateContractTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.UpdateContractTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.UpdatePolicyTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.UpdatePolicyTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.UtxSizeDto
import com.wavesenterprise.sdk.node.client.http.tx.UtxSizeDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.sign.BurnSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CallContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.ContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreateAliasSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreateContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreatePolicySignRequest
import com.wavesenterprise.sdk.node.domain.sign.DataSignRequest
import com.wavesenterprise.sdk.node.domain.sign.DisableContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.GenesisPermitSignRequest
import com.wavesenterprise.sdk.node.domain.sign.IssueSignRequest
import com.wavesenterprise.sdk.node.domain.sign.LeaseCancelSignRequest
import com.wavesenterprise.sdk.node.domain.sign.LeaseSignRequest
import com.wavesenterprise.sdk.node.domain.sign.MassTransferSignRequest
import com.wavesenterprise.sdk.node.domain.sign.PermitSignRequest
import com.wavesenterprise.sdk.node.domain.sign.RegisterNodeSignRequest
import com.wavesenterprise.sdk.node.domain.sign.ReissueSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SetAssetScriptSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SetScriptSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.sign.SponsorFeeSignRequest
import com.wavesenterprise.sdk.node.domain.sign.TransferSignRequest
import com.wavesenterprise.sdk.node.domain.sign.UpdateContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.UpdatePolicySignRequest
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.sdk.node.domain.tx.BurnTx
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateAliasTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreatePolicyTx
import com.wavesenterprise.sdk.node.domain.tx.DataTx
import com.wavesenterprise.sdk.node.domain.tx.DisableContractTx
import com.wavesenterprise.sdk.node.domain.tx.ExecutedContractTx
import com.wavesenterprise.sdk.node.domain.tx.GenesisPermitTx
import com.wavesenterprise.sdk.node.domain.tx.GenesisRegisterNodeTx
import com.wavesenterprise.sdk.node.domain.tx.GenesisTx
import com.wavesenterprise.sdk.node.domain.tx.IssueTx
import com.wavesenterprise.sdk.node.domain.tx.LeaseCancelTx
import com.wavesenterprise.sdk.node.domain.tx.LeaseTx
import com.wavesenterprise.sdk.node.domain.tx.MassTransferTx
import com.wavesenterprise.sdk.node.domain.tx.PermitTx
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.sdk.node.domain.tx.RegisterNodeTx
import com.wavesenterprise.sdk.node.domain.tx.ReissueTx
import com.wavesenterprise.sdk.node.domain.tx.SetAssetScriptTx
import com.wavesenterprise.sdk.node.domain.tx.SetScriptTx
import com.wavesenterprise.sdk.node.domain.tx.SponsorFeeTx
import com.wavesenterprise.sdk.node.domain.tx.TransferTx
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx
import com.wavesenterprise.sdk.node.domain.tx.UpdatePolicyTx
import com.wavesenterprise.sdk.node.domain.tx.UtxSize
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
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
            is CreateAliasSignRequest -> signDto(request.toDto()).toDomain()
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
            is ContractSignRequest -> when (request) {
                is CallContractSignRequest -> signDto(request.toDto()).toDomain()
                is CreateContractSignRequest -> signDto(request.toDto()).toDomain()
            }
        } as T

    private suspend inline fun <reified T : TxDto, reified R : SignRequestDto<T>> signDto(request: R): T =
        httpClient.post(nodeUrl) {
            url.appendPathSegments(Transactions.PATH, Transactions.SIGN)
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
            setBody(request)
        }.body()

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : Tx> signAndBroadcast(request: SignRequest<T>): T =
        when (request) {
            is AtomicSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is BurnSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
            is CreateAliasSignRequest -> signAndBroadcastDto(request.toDto()).toDomain()
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
            is ContractSignRequest -> when (request) {
                is CallContractSignRequest -> signDto(request.toDto()).toDomain()
                is CreateContractSignRequest -> signDto(request.toDto()).toDomain()
            }
        } as T

    private suspend inline fun <reified T : TxDto, reified R : SignRequestDto<T>> signAndBroadcastDto(request: R): T =
        httpClient.post(nodeUrl) {
            url.appendPathSegments(Transactions.PATH, Transactions.SIGN_AND_BROADCAST)
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
            is GenesisPermitTx -> broadcastDto(tx.toDto()).toDomain()
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
            url.appendPathSegments(Transactions.PATH, Transactions.BROADCAST)
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
            setBody(tx)
        }.body()

    override suspend fun utx(): List<Tx> =
        httpClient.get(nodeUrl) {
            url.appendPathSegments(Transactions.Utx.PATH)
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
        }.body<List<TxDto>>().map { it.toDomain() }

    override suspend fun utxInfo(): UtxSize =
        httpClient.get(nodeUrl) {
            url.appendPathSegments(Transactions.Utx.PATH, Transactions.Utx.SIZE)
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
        }.body<UtxSizeDto>().toDomain()

    override suspend fun txInfo(txId: TxId): TxInfo {
        val response = httpClient.get(nodeUrl) {
            url.appendPathSegments(Transactions.PATH, Transactions.INFO, txId.asBase58String())
            contentType(ContentType.Application.Json)
            accept(ContentType.Any)
        }
        val tx = response.body<TxDto>()
        return TxInfo(
            tx.height?.let { Height(it) } ?: error("Height not present, txId $txId"),
            tx.toDomain(),
        )
    }

    companion object {
        object Transactions {
            const val PATH = "transactions"
            const val SIGN = "sign"
            const val BROADCAST = "broadcast"
            const val SIGN_AND_BROADCAST = "signAndBroadcast"
            const val INFO = "info"

            object Utx {
                const val PATH = Transactions.PATH + "/" + "unconfirmed"
                const val SIZE = "size"
            }
        }
    }
}
