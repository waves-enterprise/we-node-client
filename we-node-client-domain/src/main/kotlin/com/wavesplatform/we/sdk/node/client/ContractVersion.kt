package com.wavesplatform.we.sdk.node.client

@JvmInline
value class ContractVersion(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): ContractVersion =
            ContractVersion(value)

        inline val Int.contractVersion: ContractVersion get() = ContractVersion(this)
    }
}
