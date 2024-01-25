package com.wavesenterprise.sdk.node.domain.util.processor

import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataValue

object ParamsProcessor {

    @Suppress("MagicNumber")
    private val map: Map<String, Int> =
        mapOf(
            "integer" to 0,
            "boolean" to 1,
            "binary" to 2,
            "string" to 3,
        )

    @Suppress("MagicNumber")
    fun getBytes(dataEntry: DataEntry): ByteArray {
        val keyBytes = concatBytes(numberToBytes(dataEntry.key.value.length, 2), strToBytes(dataEntry.key.value))
        val typeBytes: ByteArray
        val contentBytes: ByteArray
        val lengthBytes: ByteArray
        return when (dataEntry.value) {
            is DataValue.IntegerDataValue -> {
                typeBytes = numberToBytes(map["integer"]!!)
                contentBytes = numberToBytes((dataEntry.value).value.toInt(), 8)
                concatBytes(keyBytes, typeBytes, contentBytes)
            }
            is DataValue.BooleanDataValue -> {
                typeBytes = numberToBytes(map["boolean"]!!)
                contentBytes = boolToBytes((dataEntry.value).value)
                concatBytes(keyBytes, typeBytes, contentBytes)
            }
            is DataValue.BinaryDataValue -> {
                typeBytes = numberToBytes(map["binary"]!!)
                contentBytes = (dataEntry.value).value
                lengthBytes = numberToBytes(contentBytes.size, 4)
                concatBytes(keyBytes, typeBytes, lengthBytes, contentBytes)
            }
            is DataValue.StringDataValue -> {
                typeBytes = numberToBytes(map["string"]!!)
                contentBytes = strToBytes((dataEntry.value).value)
                lengthBytes = numberToBytes(contentBytes.size, 4)
                concatBytes(keyBytes, typeBytes, lengthBytes, contentBytes)
            }
        }
    }
}
