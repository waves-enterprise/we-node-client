package com.wavesplatform.we.sdk.node.client

@JvmInline
value class PolicyName(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): PolicyName =
            PolicyName(value)

        inline val String.policyName: PolicyName get() = PolicyName(this)
    }
}
