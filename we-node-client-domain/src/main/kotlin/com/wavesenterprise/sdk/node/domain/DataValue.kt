package com.wavesenterprise.sdk.node.domain

sealed interface DataValue {
    data class IntegerDataValue(val value: Long) : DataValue {
        companion object {
            @JvmStatic
            fun fromLong(value: Long): IntegerDataValue =
                IntegerDataValue(value)

            @Suppress("MemberNameEqualsClassName")
            inline val Long.integerDataValue: IntegerDataValue
                get() = IntegerDataValue(this)
        }
    }

    data class BooleanDataValue(val value: Boolean) : DataValue {
        companion object {
            @JvmStatic
            fun fromBoolean(value: Boolean): BooleanDataValue =
                BooleanDataValue(value)

            @Suppress("MemberNameEqualsClassName")
            inline val Boolean.booleanDataValue: BooleanDataValue
                get() = BooleanDataValue(this)
        }
    }

    data class BinaryDataValue(val value: ByteArray) : DataValue {
        companion object {
            @JvmStatic
            fun fromByteArray(value: ByteArray): BinaryDataValue =
                BinaryDataValue(value)

            @Suppress("MemberNameEqualsClassName")
            inline val ByteArray.binaryDataValue: BinaryDataValue
                get() = BinaryDataValue(this)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as BinaryDataValue

            if (!value.contentEquals(other.value)) return false

            return true
        }

        override fun hashCode(): Int {
            return value.contentHashCode()
        }
    }

    data class StringDataValue(val value: String) : DataValue {
        companion object {
            @JvmStatic
            fun fromString(value: String): StringDataValue =
                StringDataValue(value)

            @Suppress("MemberNameEqualsClassName")
            inline val String.stringDataValue: StringDataValue
                get() = StringDataValue(this)
        }
    }
}
