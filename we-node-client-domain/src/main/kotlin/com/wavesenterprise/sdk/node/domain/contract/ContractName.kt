package com.wavesenterprise.sdk.node.domain.contract

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.StringProcessor

data class ContractName(val value: String) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromString(value: String) =
            ContractName(value)

        @Suppress("MemberNameEqualsClassName")
        inline val String.contractName: ContractName get() = ContractName(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = StringProcessor.getBytes(value)
}
