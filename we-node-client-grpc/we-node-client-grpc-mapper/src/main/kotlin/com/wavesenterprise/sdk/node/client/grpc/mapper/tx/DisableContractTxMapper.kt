package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.ContractIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.FeeAssetIdMapper.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.tx.DisableContractTx
import com.wavesenterprise.transaction.protobuf.docker.DisableContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.docker.disableContractTransaction
import com.wavesenterprise.transaction.protobuf.docker.feeAssetIdOrNull

object DisableContractTxMapper {
    @JvmStatic
    fun DisableContractTx.dto(): DisableContractTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: DisableContractTx): DisableContractTransaction =
        disableContractTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            contractId = tx.contractId.byteString()
            fee = tx.fee.value
            timestamp = tx.timestamp.utcTimestampMillis
            setIfNotNull(this::feeAssetId, tx.feeAssetId) { it.bytesValue() }
            setIfNotNull(this::atomicBadge, tx.atomicBadge) { it.dto() }
            tx.proofs?.also { domainProofs: List<Signature> ->
                proofs += domainProofs.map { it.byteString() }
            }
            senderAddress = tx.senderAddress.byteString()
        }

    @JvmStatic
    fun DisableContractTransaction.domain(version: TxVersion): DisableContractTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: DisableContractTransaction, version: TxVersion): DisableContractTx =
        DisableContractTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.byteArray()),
            contractId = ContractId(
                txId = TxId(tx.contractId.byteArray()),
            ),
            fee = Fee(tx.fee),
            timestamp = Timestamp(tx.timestamp),
            feeAssetId = tx.feeAssetIdOrNull?.let {
                FeeAssetId(
                    txId = TxId(it.value.byteArray()),
                )
            },
            atomicBadge = tx.atomicBadgeOrNull?.domain(),
            proofs = tx.proofsList?.map {
                Signature(it.byteArray())
            },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
