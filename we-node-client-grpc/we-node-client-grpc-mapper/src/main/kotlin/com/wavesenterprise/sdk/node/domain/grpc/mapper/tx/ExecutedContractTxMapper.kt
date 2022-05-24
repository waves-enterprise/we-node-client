package com.wavesenterprise.sdk.node.domain.grpc.mapper.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.domain.grpc.mapper.DataEntryMapper.domain
import com.wavesenterprise.sdk.node.domain.grpc.mapper.DataEntryMapper.dto
import com.wavesenterprise.sdk.node.domain.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.grpc.mapper.HashMapper.byteString
import com.wavesenterprise.sdk.node.domain.grpc.mapper.PublicKeyMapper.byteString
import com.wavesenterprise.sdk.node.domain.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.domain.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.domain.grpc.mapper.tx.ExecutableTxMapper.domain
import com.wavesenterprise.sdk.node.domain.grpc.mapper.tx.ValidationProofMapper.domain
import com.wavesenterprise.sdk.node.domain.grpc.mapper.tx.ValidationProofMapper.dto
import com.wavesenterprise.sdk.node.domain.tx.ExecutedContractTx
import com.wavesenterprise.transaction.protobuf.docker.ExecutedContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.executedContractTransaction

object ExecutedContractTxMapper {
    @JvmStatic
    fun ExecutedContractTx.dto(): ExecutedContractTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: ExecutedContractTx): ExecutedContractTransaction =
        executedContractTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            this.tx = ExecutableTxMapper.dtoInternal(tx.tx)
            results += tx.results.map { it.dto() }
            resultsHash = tx.resultsHash.byteString()
            validationProofs += tx.validationProofs.map { it.dto() }
            timestamp = tx.timestamp.utcTimestampMillis
            tx.proofs?.also { domainProofs: List<Signature> ->
                proofs += domainProofs.map { it.byteString() }
            }
            senderAddress = tx.senderAddress.byteString()
        }

    @JvmStatic
    fun ExecutedContractTransaction.domain(version: TxVersion): ExecutedContractTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: ExecutedContractTransaction, version: TxVersion): ExecutedContractTx =
        ExecutedContractTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            tx = tx.tx.domain(),
            results = tx.resultsList.map { it.domain() },
            resultsHash = Hash(tx.resultsHash.byteArray()),
            validationProofs = tx.validationProofsList.map { it.domain() },
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            proofs = tx.proofsList.let { dtoProofs ->
                dtoProofs.map {
                    Signature(it.byteArray())
                }
            },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
