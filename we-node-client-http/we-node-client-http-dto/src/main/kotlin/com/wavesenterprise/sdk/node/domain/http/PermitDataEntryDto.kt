package com.wavesenterprise.sdk.node.domain.http

import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.DataValue
import com.wavesenterprise.sdk.node.domain.PermitDataAction
import com.wavesenterprise.sdk.node.domain.PermitDataEntry
import java.util.Base64

data class PermitDataEntryDto(
    val key: String,
    val type: String?,
    val value: Any?,
) {
    companion object {
        private val BASE_64_ENCODER = Base64.getEncoder()
        private val BASE_64_DECODER = Base64.getDecoder()

        @JvmStatic
        fun PermitDataEntry.toDto(): PermitDataEntryDto {
            val (type: String?, value: Any?) = when (val action = action) {
                PermitDataAction.DeleteDataAction -> Pair(null, null)
                is PermitDataAction.SetDataAction -> when (val value = action.value) {
                    is DataValue.BinaryDataValue -> {
                        Pair(DataEntryDto.BINARY_TYPE, BASE_64_ENCODER.encodeToString(value.value))
                    }
                    is DataValue.BooleanDataValue -> Pair(DataEntryDto.BOOLEAN_TYPE, value.value)
                    is DataValue.IntegerDataValue -> Pair(DataEntryDto.INTEGER_TYPE, value.value)
                    is DataValue.StringDataValue -> Pair(DataEntryDto.STRING_TYPE, value.value)
                }
            }
            return PermitDataEntryDto(
                key = key.value,
                type = type,
                value = value,
            )
        }

        @JvmStatic
        fun PermitDataEntryDto.toDomain(): PermitDataEntry {
            val action = when (type) {
                null -> PermitDataAction.DeleteDataAction
                else -> PermitDataAction.SetDataAction(
                    when (type) {
                        DataEntryDto.BINARY_TYPE -> DataValue.BinaryDataValue(BASE_64_DECODER.decode(value as String))
                        DataEntryDto.BOOLEAN_TYPE -> DataValue.BooleanDataValue(value as Boolean)
                        DataEntryDto.INTEGER_TYPE -> DataValue.IntegerDataValue((value as Number).toLong())
                        DataEntryDto.STRING_TYPE -> DataValue.StringDataValue(value as String)
                        else -> error("Unknown data type $type for key $key")
                    }
                )
            }
            return PermitDataEntry(
                key = DataKey(key),
                action = action
            )
        }
    }
}
