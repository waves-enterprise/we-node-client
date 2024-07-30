package com.wavesenterprise.sdk.node.client.http.tx

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.tx.Tx

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = GenesisTxDto::class, name = TxType.GENESIS_TX_TYPE_STRING),
    JsonSubTypes.Type(value = IssueTxDto::class, name = TxType.ISSUE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = TransferTxDto::class, name = TxType.TRANSFER_TX_TYPE_STRING),
    JsonSubTypes.Type(value = ReissueTxDto::class, name = TxType.REISSUE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = BurnTxDto::class, name = TxType.BURN_TX_TYPE_STRING),
    JsonSubTypes.Type(value = LeaseTxDto::class, name = TxType.LEASE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = LeaseCancelTxDto::class, name = TxType.LEASE_CANCEL_TX_TYPE_STRING),
    JsonSubTypes.Type(value = CreateAliasTxDto::class, name = TxType.CREATE_ALIAS_TX_TYPE_STRING),
    JsonSubTypes.Type(value = MassTransferTxDto::class, name = TxType.MASS_TRANSFER_TX_TYPE_STRING),
    JsonSubTypes.Type(value = DataTxDto::class, name = TxType.DATA_TX_TYPE_STRING),
    JsonSubTypes.Type(value = SetScriptTxDto::class, name = TxType.SET_SCRIPT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = SponsorFeeTxDto::class, name = TxType.SPONSOR_FEE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = SetAssetScriptTxDto::class, name = TxType.SET_ASSET_SCRIPT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = GenesisPermitTxDto::class, name = TxType.GENESIS_PERMIT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = PermitTxDto::class, name = TxType.PERMIT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = CreateContractTxDto::class, name = TxType.CREATE_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = CallContractTxDto::class, name = TxType.CALL_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = ExecutedContractTxDto::class, name = TxType.EXECUTED_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = DisableContractTxDto::class, name = TxType.DISABLE_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = UpdateContractTxDto::class, name = TxType.UPDATE_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = GenesisRegisterNodeTxDto::class, name = TxType.GENESIS_REGISTER_NODE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = RegisterNodeTxDto::class, name = TxType.REGISTER_NODE_TX_TYPE_STRING),
    JsonSubTypes.Type(value = CreatePolicyTxDto::class, name = TxType.CREATE_POLICY_TX_TYPE_STRING),
    JsonSubTypes.Type(value = UpdatePolicyTxDto::class, name = TxType.UPDATE_POLICY_TX_TYPE_STRING),
    JsonSubTypes.Type(value = PolicyDataHashTxDto::class, name = TxType.POLICY_DATA_HASH_TX_TYPE_STRING),
    JsonSubTypes.Type(value = AtomicTxDto::class, name = TxType.ATOMIC_TX_TYPE_STRING),
)
sealed interface TxDto {
    val id: String
    val type: Int
    val timestamp: Long
    val version: Int
    val height: Long?

    companion object {
        @JvmStatic
        @Suppress("CyclomaticComplexMethod")
        fun TxDto.toDomain(): Tx =
            when (this) {
                is CallContractTxDto -> CallContractTxDto.toDomainInternal(this)
                is CreateContractTxDto -> CreateContractTxDto.toDomainInternal(this)
                is CreatePolicyTxDto -> CreatePolicyTxDto.toDomainInternal(this)
                is DisableContractTxDto -> DisableContractTxDto.toDomainInternal(this)
                is ExecutedContractTxDto -> ExecutedContractTxDto.toDomainInternal(this)
                is PermitTxDto -> PermitTxDto.toDomainInternal(this)
                is PolicyDataHashTxDto -> PolicyDataHashTxDto.toDomainInternal(this)
                is TransferTxDto -> TransferTxDto.toDomainInternal(this)
                is UpdateContractTxDto -> UpdateContractTxDto.toDomainInternal(this)
                is UpdatePolicyTxDto -> UpdatePolicyTxDto.toDomainInternal(this)
                is AtomicTxDto -> AtomicTxDto.toDomainInternal(this)
                is BurnTxDto -> BurnTxDto.toDomainInternal(this)
                is CreateAliasTxDto -> CreateAliasTxDto.toDomainInternal(this)
                is DataTxDto -> DataTxDto.toDomainInternal(this)
                is GenesisPermitTxDto -> GenesisPermitTxDto.toDomainInternal(this)
                is GenesisRegisterNodeTxDto -> GenesisRegisterNodeTxDto.toDomainInternal(this)
                is GenesisTxDto -> GenesisTxDto.toDomainInternal(this)
                is IssueTxDto -> IssueTxDto.toDomainInternal(this)
                is LeaseCancelTxDto -> LeaseCancelTxDto.toDomainInternal(this)
                is LeaseTxDto -> LeaseTxDto.toDomainInternal(this)
                is MassTransferTxDto -> MassTransferTxDto.toDomainInternal(this)
                is RegisterNodeTxDto -> RegisterNodeTxDto.toDomainInternal(this)
                is ReissueTxDto -> ReissueTxDto.toDomainInternal(this)
                is SetAssetScriptTxDto -> SetAssetScriptTxDto.toDomainInternal(this)
                is SetScriptTxDto -> SetScriptTxDto.toDomainInternal(this)
                is SponsorFeeTxDto -> SponsorFeeTxDto.toDomainInternal(this)
            }
    }
}
