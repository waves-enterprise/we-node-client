package com.wavesenterprise.sdk.tx.signer

import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.BurnSignRequest
import com.wavesenterprise.sdk.node.domain.sign.BurnSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.CallContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CallContractSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.CreateAliasSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreateAliasSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.CreateContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreateContractSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.CreatePolicySignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreatePolicySignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.DataSignRequest
import com.wavesenterprise.sdk.node.domain.sign.DisableContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.DisableContractSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.IssueSignRequest
import com.wavesenterprise.sdk.node.domain.sign.IssueSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.LeaseCancelSignRequest
import com.wavesenterprise.sdk.node.domain.sign.LeaseCancelSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.LeaseSignRequest
import com.wavesenterprise.sdk.node.domain.sign.MassTransferSignRequest
import com.wavesenterprise.sdk.node.domain.sign.PermitSignRequest
import com.wavesenterprise.sdk.node.domain.sign.PermitSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.RegisterNodeSignRequest
import com.wavesenterprise.sdk.node.domain.sign.RegisterNodeSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.ReissueSignRequest
import com.wavesenterprise.sdk.node.domain.sign.ReissueSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.SetAssetScriptSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SetAssetScriptSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.SetScriptSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.sign.SponsorFeeSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SponsorFeeSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.TransferSignRequest
import com.wavesenterprise.sdk.node.domain.sign.UpdateContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.UpdateContractSignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.sign.UpdatePolicySignRequest
import com.wavesenterprise.sdk.node.domain.sign.UpdatePolicySignRequest.Companion.toTx
import com.wavesenterprise.sdk.node.domain.tx.Tx

@Suppress("CyclomaticComplexMethod")
fun SignRequest<*>.mapToTx(senderPublicKey: PublicKey, chainId: ChainId): Tx =
    when (this) {
        is IssueSignRequest -> toTx(senderPublicKey, chainId)
        is TransferSignRequest -> TODO("Will be implemented in https://jira.web3tech.ru/browse/WTCH-206")
        is ReissueSignRequest -> toTx(senderPublicKey, chainId)
        is BurnSignRequest -> toTx(senderPublicKey, chainId)
        is LeaseSignRequest -> TODO("Will be implemented in https://jira.web3tech.ru/browse/WTCH-200")
        is LeaseCancelSignRequest -> toTx(senderPublicKey, chainId)
        is CreateAliasSignRequest -> toTx(senderPublicKey)
        is MassTransferSignRequest -> TODO("Will be implemented in https://jira.web3tech.ru/browse/WTCH-201")
        is DataSignRequest -> TODO("Will be implemented in https://jira.web3tech.ru/browse/WTCH-202")
        is SetScriptSignRequest -> TODO("Will be implemented in https://jira.web3tech.ru/browse/WTCH-203")
        is SponsorFeeSignRequest -> toTx(senderPublicKey)
        is SetAssetScriptSignRequest -> toTx(senderPublicKey, chainId)
        is PermitSignRequest -> toTx(senderPublicKey)
        is CreateContractSignRequest -> toTx(senderPublicKey)
        is CallContractSignRequest -> toTx(senderPublicKey)
        is DisableContractSignRequest -> toTx(senderPublicKey)
        is UpdateContractSignRequest -> toTx(senderPublicKey)
        is RegisterNodeSignRequest -> toTx(senderPublicKey)
        is CreatePolicySignRequest -> toTx(senderPublicKey)
        is UpdatePolicySignRequest -> toTx(senderPublicKey)
        is AtomicSignRequest -> toTx(senderPublicKey)
        else -> throw IllegalArgumentException(
            "The transaction ${this.javaClass.simpleName} does not require signing or is not supported by signer",
        )
    }
