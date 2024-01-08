package com.wavesenterprise.sdk.node.domain.address

data class Message(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): Message =
            Message(value)

        inline val String.message: Message get() = Message(this)
    }
}
