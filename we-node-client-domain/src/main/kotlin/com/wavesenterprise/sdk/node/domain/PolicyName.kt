package com.wavesenterprise.sdk.node.domain

data class PolicyName(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): PolicyName =
            PolicyName(value)

        inline val String.policyName: PolicyName get() = PolicyName(this)
    }
}
