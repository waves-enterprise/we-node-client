package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.TransferTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Attachment
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.tx.TransferTx
import com.wavesenterprise.transaction.protobuf.atomicBadge
import com.wavesenterprise.transaction.protobuf.transfer.transferTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TransferTxMapperTest {

    @Test
    fun `should map to TransferTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = transferTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            assetId = bytesValue {
                byteString("assetId")
            }
            feeAssetId = bytesValue {
                byteString("feeAssetId")
            }
            timestamp = 1716881331027L
            amount = 1_000_000L
            fee = 10L
            recipient = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            attachment = byteString("attachment")
            atomicBadge = atomicBadge {
                trustedSender = bytesValue {
                    byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
                }
            }
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                TransferTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    assetId = AssetId(grpcTx.assetId.value.byteArray()),
                    feeAssetId = FeeAssetId(
                        txId = TxId(grpcTx.feeAssetId.value.byteArray()),
                    ),
                    timestamp = Timestamp(grpcTx.timestamp),
                    amount = Amount(grpcTx.amount),
                    fee = Fee(grpcTx.fee),
                    recipient = Address(grpcTx.recipient.byteArray()),
                    attachment = Attachment(grpcTx.attachment.byteArray()),
                    atomicBadge = AtomicBadge(
                        trustedSender = Address(grpcTx.atomicBadge.trustedSender.value.byteArray()),
                    ),
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                ),
            )
    }

    @Test
    fun `should map to TransferTx (with nulls)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = transferTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            clearAssetId()
            clearFeeAssetId()
            timestamp = 1716881331027L
            amount = 1_000_000L
            fee = 10L
            recipient = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            attachment = byteString("attachment")
            clearAtomicBadge()
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                TransferTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    assetId = null,
                    feeAssetId = null,
                    timestamp = Timestamp(grpcTx.timestamp),
                    amount = Amount(grpcTx.amount),
                    fee = Fee(grpcTx.fee),
                    recipient = Address(grpcTx.recipient.byteArray()),
                    attachment = Attachment(grpcTx.attachment.byteArray()),
                    atomicBadge = null,
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                ),
            )
    }
}
