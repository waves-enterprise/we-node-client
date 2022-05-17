package com.wavesplatform.we.sdk.node.client

sealed interface DataValue {
    @JvmInline
    value class IntegerDataValue(val value: Long) : DataValue {
        companion object {
            @JvmStatic
            fun fromLong(value: Long): IntegerDataValue =
                IntegerDataValue(value)

            inline val Long.integerDataValue: IntegerDataValue
                get() = IntegerDataValue(this)
        }
    }

    @JvmInline
    value class BooleanDataValue(val value: Boolean) : DataValue {
        companion object {
            @JvmStatic
            fun fromBoolean(value: Boolean): BooleanDataValue =
                BooleanDataValue(value)

            inline val Boolean.booleanDataValue: BooleanDataValue
                get() = BooleanDataValue(this)
        }
    }

    @JvmInline
    value class BinaryDataValue(val value: ByteArray) : DataValue {
        companion object {
            @JvmStatic
            fun fromByteArray(value: ByteArray): BinaryDataValue =
                BinaryDataValue(value)

            inline val ByteArray.binaryDataValue: BinaryDataValue
                get() = BinaryDataValue(this)
        }
    }

    @JvmInline
    value class StringDataValue(val value: String) : DataValue {
        companion object {
            @JvmStatic
            fun fromString(value: String): StringDataValue =
                StringDataValue(value)

            inline val String.stringDataValue: StringDataValue
                get() = StringDataValue(this)
        }
    }
}
