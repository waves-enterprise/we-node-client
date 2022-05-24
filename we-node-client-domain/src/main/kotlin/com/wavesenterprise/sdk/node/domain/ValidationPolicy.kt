package com.wavesenterprise.sdk.node.domain

sealed interface ValidationPolicy {
    object Any : ValidationPolicy
    object Majority : ValidationPolicy
    data class MajorityWithOneOf(
        val addresses: List<Address>,
    ) : ValidationPolicy {
        init {
            check(addresses.isNotEmpty()) {
                "Addresses must not be empty"
            }
        }
    }
}
