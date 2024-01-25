package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.concatBytes
import com.wavesenterprise.sdk.node.domain.util.processor.numberToBytes

sealed interface ValidationPolicy : SerializableToBytes {

    val type: Int

    override fun getSignatureBytes(networkByte: Byte?): ByteArray {
        val typeByte = numberToBytes(this.type)
        return concatBytes(typeByte)
    }

    object Any : ValidationPolicy {
        override val type: Int = 0
    }

    object Majority : ValidationPolicy {
        override val type: Int = 1
    }
    data class MajorityWithOneOf(
        val addresses: List<Address>,
    ) : ValidationPolicy {
        override val type: Int = 2

        @Suppress("MagicNumber", "SpreadOperator")
        override fun getSignatureBytes(networkByte: Byte?): ByteArray {
            val typeByte = super.getSignatureBytes(networkByte)
            val lengthBytes = numberToBytes(addresses.size, 2)
            val addressesBytes = addresses.map { it.bytes }
            return concatBytes(typeByte, lengthBytes, *addressesBytes.toTypedArray())
        }

        init {
            check(addresses.isNotEmpty()) {
                "Addresses must not be empty"
            }
        }
    }
    companion object {

        @JvmStatic
        fun ValidationPolicy.getType() = when (this) {
            is Any -> 0
            is Majority -> 1
            is MajorityWithOneOf -> 2
        }
    }
}
