package com.wavesenterprise.sdk.node.domain

data class PolicyDescription(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): PolicyDescription =
            PolicyDescription(value)

        inline val String.policyDescription: PolicyDescription get() = PolicyDescription(this)
    }
}
