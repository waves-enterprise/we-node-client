package com.wavesenterprise.sdk.node.domain.contract

data class ContractImageHash(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): ContractImageHash =
            ContractImageHash(value)

        inline val String.contractName: ContractImageHash
            get() = ContractImageHash(this)
    }
}
