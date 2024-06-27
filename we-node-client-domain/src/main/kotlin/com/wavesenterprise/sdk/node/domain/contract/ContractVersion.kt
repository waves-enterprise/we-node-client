package com.wavesenterprise.sdk.node.domain.contract

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.IntProcessor

data class ContractVersion(val value: Int) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): ContractVersion =
            ContractVersion(value)

        @JvmStatic
        fun ContractVersion.update() = ContractVersion(this.value + 1)

        inline val Int.contractVersion: ContractVersion get() = ContractVersion(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = IntProcessor.getBytes(value)
}
