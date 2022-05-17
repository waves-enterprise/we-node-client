package com.wavesplatform.we.sdk.node.client.http.tx

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.tx.Tx

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = CreateContractTxDto::class, name = TxType.CREATE_CONTRACT_TX_TYPE_STRING),
)
sealed interface TxDto {
    val id: String
    val type: Int
    val timestamp: Long
    val version: Int
    val height: Long?

    companion object {
        @JvmStatic
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
