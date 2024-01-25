package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.StringProcessor

data class PolicyName(val value: String) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromString(value: String): PolicyName =
            PolicyName(value)

        inline val String.policyName: PolicyName get() = PolicyName(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = StringProcessor.getBytes(value)
}
