package com.wavesenterprise.sdk.node.client.feign.tx

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
import com.wavesenterprise.sdk.node.client.http.tx.AtomicTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.BurnTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.CallContractTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.CreateAliasTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.CreateContractTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.CreatePolicyTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.DataTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.DisableContractTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.ExecutedContractTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.GenesisPermitTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.GenesisRegisterNodeTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.GenesisTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.IssueTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.LeaseCancelTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.LeaseTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.MassTransferTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.PermitTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.PolicyDataHashTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.RegisterNodeTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.ReissueTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.SetAssetScriptTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.SetScriptTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.SponsorFeeTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.TransferTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.TxDto
import com.wavesenterprise.sdk.node.client.http.tx.UpdateContractTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.UpdatePolicyTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.sign.BurnSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CallContractSignRequest
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
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx
import com.wavesenterprise.sdk.node.domain.tx.UpdatePolicyTx

@Suppress("CyclomaticComplexMethod")
fun <T : Tx, D : TxDto> mapDto(request: SignRequest<T>): SignRequestDto<D> =
    when (request) {
        is AtomicSignRequest -> request.toDto() // todo extract lambda for reuse?
        is BurnSignRequest -> request.toDto()
        is CreateAliasSignRequest -> request.toDto()
        is CreatePolicySignRequest -> request.toDto()
        is DataSignRequest -> request.toDto()
        is DisableContractSignRequest -> request.toDto()
        is GenesisPermitSignRequest -> request.toDto()
        is IssueSignRequest -> request.toDto()
        is LeaseCancelSignRequest -> request.toDto()
        is LeaseSignRequest -> request.toDto()
        is MassTransferSignRequest -> request.toDto()
        is PermitSignRequest -> request.toDto()
        is RegisterNodeSignRequest -> request.toDto()
        is ReissueSignRequest -> request.toDto()
        is SetAssetScriptSignRequest -> request.toDto()
        is SetScriptSignRequest -> request.toDto()
        is SponsorFeeSignRequest -> request.toDto()
        is TransferSignRequest -> request.toDto()
        is UpdateContractSignRequest -> request.toDto()
        is UpdatePolicySignRequest -> request.toDto()
        is CallContractSignRequest -> request.toDto()
        is CreateContractSignRequest -> request.toDto()
    } as SignRequestDto<D>

@Suppress("CyclomaticComplexMethod")
fun <T : Tx, D : TxDto> mapDto(tx: T): D =
    when (val tx = tx as Tx) {
        is AtomicTx -> tx.toDto()
        is BurnTx -> tx.toDto()
        is CallContractTx -> tx.toDto()
        is CreateAliasTx -> tx.toDto()
        is CreateContractTx -> tx.toDto()
        is CreatePolicyTx -> tx.toDto()
        is DataTx -> tx.toDto()
        is DisableContractTx -> tx.toDto()
        is ExecutedContractTx -> tx.toDto()
        is GenesisPermitTx -> tx.toDto()
        is GenesisRegisterNodeTx -> tx.toDto()
        is GenesisTx -> tx.toDto()
        is IssueTx -> tx.toDto()
        is LeaseCancelTx -> tx.toDto()
        is LeaseTx -> tx.toDto()
        is MassTransferTx -> tx.toDto()
        is PermitTx -> tx.toDto()
        is PolicyDataHashTx -> tx.toDto()
        is RegisterNodeTx -> tx.toDto()
        is ReissueTx -> tx.toDto()
        is SetAssetScriptTx -> tx.toDto()
        is SetScriptTx -> tx.toDto()
        is SponsorFeeTx -> tx.toDto()
        is TransferTx -> tx.toDto()
        is UpdateContractTx -> tx.toDto()
        is UpdatePolicyTx -> tx.toDto()
    } as D
