package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion

data class GenesisTx(
    override val id: TxId,
    val recipient: Address,
    val amount: Amount,
    val fee: Fee,
    override val timestamp: Timestamp,
    val signature: Signature,
    override val version: TxVersion,
) : Tx {
    override fun withId(id: TxId): Tx = copy(id = id)

    override fun withProof(proof: Signature): Tx = copy(signature = proof)

    override fun withSenderAddress(senderAddress: Address): Tx = this
}
