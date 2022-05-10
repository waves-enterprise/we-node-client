package com.wavesplatform.we.sdk.node.client

@JvmInline
value class ContractName(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String) =
            ContractName(value)

        inline val String.contractName: ContractName get() = ContractName(this)
    }
}
