package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes

enum class Role(val code: Int) : SerializableToBytes {
    MINER(1),
    ISSUER(2),
    DEX(3),
    PERMISSIONER(4),
    BLACKLISTER(5),
    BANNED(6),
    CONTRACT_DEVELOPER(7),
    CONNECTION_MANAGER(8),
    SENDER(9),
    CONTRACT_VALIDATOR(10),
    ;

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = byteArrayOf(this.code.toByte())
}
