package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.sign.FieldInfo

data class AtomicTx(
    override val id: TxId,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 1)
    val senderPublicKey: PublicKey,
    val miner: Address? = null,
    @FieldInfo(required = true, sinceVersion = 2, bytesPosition = 2)
    val txs: List<AtomicInnerTx>,
    @FieldInfo(required = true, sinceVersion = 2, bytesPosition = 3)
    override val timestamp: Timestamp,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    val fee: Fee, // todo: ask to add field to proto
    override val version: TxVersion,
) : Tx {
    override fun withId(id: TxId): Tx = copy(id = id)

    override fun withProof(proof: Signature): Tx = copy(proofs = proofs?.plus(proof) ?: listOf(proof))

    override fun withSenderAddress(senderAddress: Address): Tx = copy(senderAddress = senderAddress)

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
