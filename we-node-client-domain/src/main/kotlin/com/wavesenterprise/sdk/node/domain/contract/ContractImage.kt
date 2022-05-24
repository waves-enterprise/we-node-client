package com.wavesenterprise.sdk.node.domain.contract

data class ContractImage(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): ContractImage =
            ContractImage(value)

        inline val String.contractImage: ContractImage get() = ContractImage(this)
    }
}
