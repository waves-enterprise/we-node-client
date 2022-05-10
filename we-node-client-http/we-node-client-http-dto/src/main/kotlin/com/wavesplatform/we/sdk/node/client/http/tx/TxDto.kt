package com.wavesplatform.we.sdk.node.client.http.tx

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.wavesplatform.we.sdk.node.client.TxType

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
}
