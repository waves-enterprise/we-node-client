package com.wavesplatform.we.sdk.node.client

sealed interface DataValue {
    @JvmInline
    value class IntDataValue(val value: Int) : DataValue {
        companion object {
            @JvmStatic
            fun fromInt(value: Int): IntDataValue =
                IntDataValue(value)

            inline val Int.intDataValue: IntDataValue
                get() = IntDataValue(this)
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
