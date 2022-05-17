package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.wavesenterprise.transaction.protobuf.dataEntry
import com.wavesplatform.we.sdk.node.client.DataEntry
import com.wavesplatform.we.sdk.node.client.DataKey
import com.wavesplatform.we.sdk.node.client.DataValue
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesenterprise.transaction.protobuf.DataEntry as ProtoDataEntry

object DataEntryMapper {
    @JvmStatic
    fun DataEntry.dto(): ProtoDataEntry =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(dataEntry: DataEntry): ProtoDataEntry =
        dataEntry {
            key = dataEntry.key.value
            when (val value = dataEntry.value) {
                is DataValue.BinaryDataValue -> binaryValue = value.value.byteString()
                is DataValue.BooleanDataValue -> boolValue = value.value
                is DataValue.IntegerDataValue -> intValue = value.value
                is DataValue.StringDataValue -> stringValue = value.value
            }
        }

    @JvmStatic
    fun ProtoDataEntry.domain(): DataEntry =
        domainInternal(this)

    @JvmStatic
    internal fun domainInternal(dataEntry: ProtoDataEntry): DataEntry =
        DataEntry(
            key = DataKey(dataEntry.key),
            value = when (dataEntry.valueCase) {
                ProtoDataEntry.ValueCase.INT_VALUE -> DataValue.IntegerDataValue(dataEntry.intValue)
                ProtoDataEntry.ValueCase.BOOL_VALUE -> DataValue.BooleanDataValue(dataEntry.boolValue)
                ProtoDataEntry.ValueCase.BINARY_VALUE -> DataValue.BinaryDataValue(dataEntry.binaryValue.byteArray())
                ProtoDataEntry.ValueCase.STRING_VALUE -> DataValue.StringDataValue(dataEntry.stringValue)
                ProtoDataEntry.ValueCase.VALUE_NOT_SET, null -> error("Value not set")
            }
        )
}
