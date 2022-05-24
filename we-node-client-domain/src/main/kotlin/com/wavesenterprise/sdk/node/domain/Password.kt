package com.wavesenterprise.sdk.node.domain

data class Password(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): Password =
            Password(value)

        inline val String.password: Password get() = Password(this)
    }
}
