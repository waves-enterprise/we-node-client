package com.wavesenterprise.sdk.node.domain.contract

data class ContractVersion(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int):
            ContractVersion =
            ContractVersion(value)

        inline val Int.contractVersion: ContractVersion get() = ContractVersion(this)
    }
}
