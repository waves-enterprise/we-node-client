package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.Base58Processor
import com.wavesenterprise.sdk.node.domain.util.processor.LongProcessor
import com.wavesenterprise.sdk.node.domain.util.processor.concatBytes

data class Transfer(
    val recipient: Address,
    val amount: Amount,
) : SerializableToBytes {
    override fun getSignatureBytes(networkByte: Byte?): ByteArray {
        val recipientBytes = Base58Processor.getBytes(this.recipient.bytes)
        val amountBytes = LongProcessor.getBytes(this.amount.value)
        return concatBytes(recipientBytes, amountBytes)
    }
}
