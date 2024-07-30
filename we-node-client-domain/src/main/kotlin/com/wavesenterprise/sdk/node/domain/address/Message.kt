package com.wavesenterprise.sdk.node.domain.address

data class Message(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): Message =
            Message(value)

        @Suppress("MemberNameEqualsClassName")
        inline val String.message: Message get() = Message(this)
    }
}
