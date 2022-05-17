package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.acl.PermitTransaction
import com.wavesenterprise.transaction.protobuf.acl.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.acl.permitTransaction
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AtomicBadgeMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PermissionOpMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PermissionOpMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesplatform.we.sdk.node.client.tx.PermitTx

object PermitTxMapper {
    @JvmStatic
    fun PermitTx.dto(): PermitTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: PermitTx): PermitTransaction =
        permitTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            target = tx.target.byteString()
            timestamp = tx.timestamp.utcTimestampMillis
            fee = tx.fee.value
            permissionOp = tx.permissionOp.dto()
            setIfNotNull(this::atomicBadge, tx.atomicBadge) { it.dto() }
            tx.proofs?.also { domainProofs: List<Signature> ->
                proofs += domainProofs.map { it.byteString() }
            }
            senderAddress = tx.senderAddress.byteString()
        }

    @JvmStatic
    fun PermitTransaction.domain(version: TxVersion): PermitTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: PermitTransaction, version: TxVersion): PermitTx =
        PermitTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            target = Address(tx.target.byteArray()),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            fee = Fee(tx.fee),
            permissionOp = tx.permissionOp.domain(),
            atomicBadge = tx.atomicBadgeOrNull?.domain(),
            proofs = tx.proofsList?.let { dtoProofs ->
                dtoProofs.map {
                    Signature(it.byteArray())
                }
            },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
