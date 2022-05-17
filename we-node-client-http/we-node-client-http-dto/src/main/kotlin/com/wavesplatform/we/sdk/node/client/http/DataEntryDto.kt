package com.wavesplatform.we.sdk.node.client.http

import com.wavesplatform.we.sdk.node.client.DataEntry
import com.wavesplatform.we.sdk.node.client.DataKey
import com.wavesplatform.we.sdk.node.client.DataValue
import java.util.Base64

data class DataEntryDto(
    val key: String,
    val type: String,
    val value: Any,
) {
    companion object {
        const val BINARY_TYPE = "binary"
        const val BOOLEAN_TYPE = "bool"
        const val INTEGER_TYPE = "integer"
        const val STRING_TYPE = "string"

        private val BASE_64_ENCODER = Base64.getEncoder()
        private val BASE_64_DECODER = Base64.getDecoder()

        @JvmStatic
        fun DataEntry.toDto(): DataEntryDto {
            val (type: String, value: Any) = when (val value = value) {
                is DataValue.BinaryDataValue -> BINARY_TYPE to BASE_64_ENCODER.encodeToString(value.value)
                is DataValue.BooleanDataValue -> BOOLEAN_TYPE to value.value
                is DataValue.IntegerDataValue -> INTEGER_TYPE to value.value
                is DataValue.StringDataValue -> STRING_TYPE to value.value
            }
            return DataEntryDto(
                key.value,
                type,
                value,
            )
        }

        @JvmStatic
        fun DataEntryDto.toDomain(): DataEntry =
            DataEntry(
                key = DataKey(key),
                value = when (type) {
                    BINARY_TYPE -> DataValue.BinaryDataValue(BASE_64_DECODER.decode(value as String))
                    BOOLEAN_TYPE -> DataValue.BooleanDataValue(value as Boolean)
                    INTEGER_TYPE -> DataValue.IntegerDataValue((value as Number).toLong())
                    STRING_TYPE -> DataValue.StringDataValue(value as String)
                    else -> error("Unknown data type $type for key $key")
                },
            )
    }
}
