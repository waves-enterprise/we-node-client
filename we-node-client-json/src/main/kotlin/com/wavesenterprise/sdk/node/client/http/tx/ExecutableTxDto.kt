package com.wavesenterprise.sdk.node.client.http.tx

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.sdk.node.domain.tx.ExecutableTx
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = CreateContractTxDto::class, name = TxType.CREATE_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = CallContractTxDto::class, name = TxType.CALL_CONTRACT_TX_TYPE_STRING),
    JsonSubTypes.Type(value = UpdateContractTxDto::class, name = TxType.UPDATE_CONTRACT_TX_TYPE_STRING),
)
sealed interface ExecutableTxDto {
    companion object {
        @JvmStatic
        fun ExecutableTx.toDto(): ExecutableTxDto =
            when (val tx = this) {
                is CallContractTx -> CallContractTxDto.toDtoInternal(tx)
                is CreateContractTx -> CreateContractTxDto.toDtoInternal(tx)
                is UpdateContractTx -> UpdateContractTxDto.toDtoInternal(tx)
            }

        @JvmStatic
        fun ExecutableTxDto.toDomain(): ExecutableTx =
            when (val tx = this) {
                is CallContractTxDto -> CallContractTxDto.toDomainInternal(tx)
                is CreateContractTxDto -> CreateContractTxDto.toDomainInternal(tx)
                is UpdateContractTxDto -> UpdateContractTxDto.toDomainInternal(tx)
            }
    }
}
