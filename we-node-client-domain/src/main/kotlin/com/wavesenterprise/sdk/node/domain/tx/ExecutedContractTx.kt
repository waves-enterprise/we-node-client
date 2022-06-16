package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.ValidationProof

data class ExecutedContractTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val tx: ExecutableTx,
    val results: List<DataEntry>,
    val resultsHash: Hash,
    val validationProofs: List<ValidationProof>,
    override val timestamp: Timestamp,
    val proofs: List<Signature>,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx, AtomicInnerTx
