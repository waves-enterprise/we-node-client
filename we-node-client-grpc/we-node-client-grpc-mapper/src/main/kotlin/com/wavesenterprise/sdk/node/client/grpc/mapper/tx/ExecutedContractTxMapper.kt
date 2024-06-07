package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.ByteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.DataEntryMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.DataEntryMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.HashMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.ExecutableTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.ValidationProofMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.ValidationProofMapper.dto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
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
            resultsHash = tx.resultsHash?.byteString() ?: ByteString.EMPTY
            validationProofs += tx.validationProofs?.map { it.dto() } ?: emptyList()
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
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            tx = tx.tx.domain(),
            results = tx.resultsList.map { it.domain() },
            resultsHash = Hash(tx.resultsHash.byteArray()),
            fee = Fee(0),
            validationProofs = tx.validationProofsList.map { it.domain() },
            timestamp = Timestamp(tx.timestamp),
            atomicBadge = null,
            proofs = tx.proofsList.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
