package com.wavesplatform.we.sdk.node.client.http.sign

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.TxDto

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = CreateContractSignRequestDto::class, name = TxType.CREATE_CONTRACT_TX_TYPE_STRING),
)
sealed interface SignRequestDto<T : TxDto> {
    val type: Int
}
