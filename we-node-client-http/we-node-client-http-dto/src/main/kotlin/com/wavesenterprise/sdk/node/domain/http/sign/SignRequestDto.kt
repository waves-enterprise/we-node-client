package com.wavesenterprise.sdk.node.domain.http.sign

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.tx.TxDto

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = IssueSignRequestDto::class, name = TxType.ISSUE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = TransferSignRequestDto::class, name = TxType.TRANSFER_TX_TYPE_STRING),
    JsonSubTypes.Type(value = ReissueSignRequestDto::class, name = TxType.REISSUE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = BurnSignRequestDto::class, name = TxType.BURN_TX_TYPE_STRING),
    JsonSubTypes.Type(value = LeaseSignRequestDto::class, name = TxType.LEASE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = LeaseCancelSignRequestDto::class, name = TxType.LEASE_CANCEL_TX_TYPE_STRING),
    JsonSubTypes.Type(value = CreateAliasSignRequestDto::class, name = TxType.CREATE_ALIAS_TX_TYPE_STRING),
    JsonSubTypes.Type(value = MassTransferSignRequestDto::class, name = TxType.MASS_TRANSFER_TX_TYPE_STRING),
    JsonSubTypes.Type(value = DataSignRequestDto::class, name = TxType.DATA_TX_TYPE_STRING),
    JsonSubTypes.Type(value = SetScriptSignRequestDto::class, name = TxType.SET_SCRIPT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = SponsorFeeSignRequestDto::class, name = TxType.SPONSOR_FEE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = SetAssetScriptSignRequestDto::class, name = TxType.SET_ASSET_SCRIPT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = GenesisPermitSignRequestDto::class, name = TxType.GENESIS_PERMIT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = PermitSignRequestDto::class, name = TxType.PERMIT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = CreateContractSignRequestDto::class, name = TxType.CREATE_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = CallContractSignRequestDto::class, name = TxType.CALL_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = DisableContractSignRequestDto::class, name = TxType.DISABLE_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = UpdateContractSignRequestDto::class, name = TxType.UPDATE_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = RegisterNodeSignRequestDto::class, name = TxType.REGISTER_NODE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = CreatePolicySignRequestDto::class, name = TxType.CREATE_POLICY_TX_TYPE_STRING),
    JsonSubTypes.Type(value = UpdatePolicySignRequestDto::class, name = TxType.UPDATE_POLICY_TX_TYPE_STRING),
    JsonSubTypes.Type(value = AtomicSignRequestDto::class, name = TxType.ATOMIC_TX_TYPE_STRING),
)
sealed interface SignRequestDto<T : TxDto> {
    val type: Int
}
