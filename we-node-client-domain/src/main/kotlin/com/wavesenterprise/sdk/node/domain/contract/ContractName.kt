package com.wavesenterprise.sdk.node.domain.contract

data class ContractName(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String) =
            ContractName(value)

        inline val String.contractName: ContractName get() = ContractName(this)
    }
}
