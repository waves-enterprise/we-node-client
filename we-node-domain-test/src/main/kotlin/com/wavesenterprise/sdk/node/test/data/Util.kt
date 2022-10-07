package com.wavesenterprise.sdk.node.test.data

import java.util.UUID

class Util private constructor() {

    companion object {
        @JvmStatic
        fun randomBytesFromUUID() = UUID.randomUUID().toString().toByteArray()
    }
}
