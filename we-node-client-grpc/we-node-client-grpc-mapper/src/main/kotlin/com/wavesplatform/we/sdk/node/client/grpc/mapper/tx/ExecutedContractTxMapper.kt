package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.docker.ExecutedContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.executedContractTransaction
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.DataEntryMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.DataEntryMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesplatform.we.sdk.node.client.grpc.mapper.HashMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.ExecutableTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.ValidationProofMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.ValidationProofMapper.dto
import com.wavesplatform.we.sdk.node.client.tx.ExecutedContractTx

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
