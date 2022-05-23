package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.TxId.Companion.txId
import com.wavesplatform.we.sdk.node.client.base58.WeBase58

data class PolicyId(val txId: TxId) {
    fun asBase58String(): String =
        txId.asBase58String()

    companion object {
        @JvmStatic
        fun fromTxId(txId: TxId): PolicyId =
            PolicyId(txId)

        @JvmStatic
        fun fromByteArray(bytes: ByteArray): PolicyId =
            PolicyId(
                bytes.txId
            )

        @JvmStatic
        fun fromBase58(string: String): PolicyId =
            fromByteArray(
                WeBase58.decode(string)
            )

        inline val TxId.policyId: PolicyId get() = PolicyId(this)

        inline val ByteArray.policyId: PolicyId get() = fromByteArray(this)

        inline val String.base58PolicyId: PolicyId get() = fromBase58(this)
    }
}
