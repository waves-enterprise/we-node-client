package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion

data class AtomicTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val miner: Address? = null,
    val txs: List<AtomicInnerTx>,
    override val timestamp: Timestamp,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    val fee: Fee, // todo: ask to add field to proto
    override val version: TxVersion,
) : Tx {
    init {
        txs
            .asSequence()
            .mapNotNull { tx: AtomicInnerTx ->
                when (tx) {
                    is CallContractTx -> tx.senderAddress to tx.atomicBadge?.trustedSender
                    is CreateContractTx -> tx.senderAddress to tx.atomicBadge?.trustedSender
                    is CreatePolicyTx -> tx.senderAddress to tx.atomicBadge?.trustedSender
                    is DisableContractTx -> tx.senderAddress to tx.atomicBadge?.trustedSender
                    is ExecutedContractTx -> null
                    is PermitTx -> tx.senderAddress to tx.atomicBadge?.trustedSender
                    is PolicyDataHashTx -> tx.senderAddress to tx.atomicBadge?.trustedSender
                    is TransferTx -> tx.senderAddress to tx.atomicBadge?.trustedSender
                    is UpdateContractTx -> tx.senderAddress to tx.atomicBadge?.trustedSender
                    is UpdatePolicyTx -> tx.senderAddress to tx.atomicBadge?.trustedSender
                }?.let { senders ->
                    tx to senders
                }
            }.forEach { (tx, senders) ->
                val (innerTxSenderAddress, innerTxTrustedSenderAddress) = senders
                if (innerTxTrustedSenderAddress == null)
                    require(innerTxSenderAddress == senderAddress) {
                        "SenderAddress of inner tx must be equal to senderAddress of atomicTx" +
                            " when trustedSender is null" +
                            " failed txId ${tx.id.asBase58String()}"
                        " atomic tx $this"
                    }
                else
                    require(innerTxTrustedSenderAddress == senderAddress) {
                        "Address of trustedSender must be equal to senderAddress of atomicTx" +
                            " when trustedSender is not null" +
                            " failed txId ${tx.id.asBase58String()}"
                        " atomic tx $this"
                    }
            }
    }
}
