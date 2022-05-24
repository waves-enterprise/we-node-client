package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion

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
