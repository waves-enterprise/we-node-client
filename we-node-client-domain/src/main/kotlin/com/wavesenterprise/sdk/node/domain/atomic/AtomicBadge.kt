package com.wavesenterprise.sdk.node.domain.atomic

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.base58.WeBase58
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.concatBytes

data class AtomicBadge(val trustedSender: Address? = null) : SerializableToBytes {
    override fun getSignatureBytes(networkByte: Byte?): ByteArray {
        val addressBytes = trustedSender?.bytes ?: WeBase58.decode("")
        val lengthBytes = if (trustedSender == null) byteArrayOf(0) else byteArrayOf(1)
        return concatBytes(lengthBytes, addressBytes)
    }
}
