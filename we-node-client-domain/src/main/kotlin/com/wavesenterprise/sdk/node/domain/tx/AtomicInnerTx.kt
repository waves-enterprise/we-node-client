package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.atomic.HasAtomicBadge
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.Base58Processor

sealed interface AtomicInnerTx : Tx, HasAtomicBadge, SerializableToBytes {

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = Base58Processor.getBytes(this.id.bytes)
}
