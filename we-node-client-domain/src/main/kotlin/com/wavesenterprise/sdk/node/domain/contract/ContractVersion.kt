package com.wavesenterprise.sdk.node.domain.contract

data class ContractVersion(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int):
            ContractVersion =
            ContractVersion(value)

        @JvmStatic
        fun ContractVersion.update() = ContractVersion(this.value + 1)

        inline val Int.contractVersion: ContractVersion get() = ContractVersion(this)
    }
}
