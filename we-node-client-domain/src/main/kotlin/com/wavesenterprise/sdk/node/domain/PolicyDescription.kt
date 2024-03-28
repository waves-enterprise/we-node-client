package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.StringProcessor

data class PolicyDescription(val value: String) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromString(value: String): PolicyDescription =
            PolicyDescription(value)

        inline val String.policyDescription: PolicyDescription get() = PolicyDescription(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = StringProcessor.getBytes(value)
}
