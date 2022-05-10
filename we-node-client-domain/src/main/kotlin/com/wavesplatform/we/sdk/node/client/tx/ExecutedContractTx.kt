package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.DataEntry
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.ValidationProof

data class ExecutedContractTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val tx: ExecutableTx,
    val results: List<DataEntry>,
    val resultsHash: Hash,
    val validationProofs: List<ValidationProof>,
    override val timestamp: Timestamp,
    val proofs: List<Proof>,
    val senderAddress: Address,
) : Tx, AtomicInnerTx
