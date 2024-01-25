package com.wavesenterprise.sdk.node.domain.contract

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.StringProcessor

data class ContractImageHash(val value: String) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromString(value: String): ContractImageHash =
            ContractImageHash(value)

        inline val String.contractName: ContractImageHash
            get() = ContractImageHash(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = StringProcessor.getBytes(value)
}
