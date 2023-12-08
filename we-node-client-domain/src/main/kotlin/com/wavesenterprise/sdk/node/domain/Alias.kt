package com.wavesenterprise.sdk.node.domain

data class Alias(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(str: String): Alias =
            Alias(str)

        @JvmStatic
        fun String.toDomain(): Alias = fromString(this.substring(ALIAS_START_INDEX))

        private const val ALIAS_START_INDEX = 8

        inline val String.alias: Alias get() = Alias(this)
    }
}
