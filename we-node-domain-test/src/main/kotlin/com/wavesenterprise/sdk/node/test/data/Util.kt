package com.wavesenterprise.sdk.node.test.data

import com.wavesenterprise.sdk.node.domain.base58.WeBase58
import java.util.UUID

class Util private constructor() {

    companion object {
        @JvmStatic
        fun randomBytesFromUUID() = UUID.randomUUID().toString().toByteArray()

        @JvmStatic
        fun randomStringBase58() = WeBase58.encode(randomBytesFromUUID())
    }
}
