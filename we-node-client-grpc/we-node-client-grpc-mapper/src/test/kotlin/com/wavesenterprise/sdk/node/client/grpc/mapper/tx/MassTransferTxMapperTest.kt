package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.MassTransferTxMapper.domain
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
import com.wavesenterprise.sdk.node.domain.Transfer
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.MassTransferTx
import com.wavesenterprise.transaction.protobuf.transfer
import com.wavesenterprise.transaction.protobuf.transfer.massTransferTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class MassTransferTxMapperTest {

    @Test
    fun `should map to MassTransferTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = massTransferTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            assetId = bytesValue {
                byteString("assetId")
            }
            feeAssetId = bytesValue {
                byteString("feeAssetId")
            }
            transfers += transfer {
                recipient = byteString("recipient")
                amount = 100L
            }
            timestamp = 1716881331027L
            fee = 10L
            attachment = byteString("attachment")
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                MassTransferTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    assetId = AssetId(grpcTx.assetId.value.byteArray()),
                    transfers = listOf(
                        Transfer(
                            recipient = Address("recipient".toByteArray()),
                            amount = Amount(100L),
                        )
                    ),
                    feeAssetId = FeeAssetId(
                        txId = TxId(grpcTx.feeAssetId.value.byteArray()),
                    ),
                    timestamp = Timestamp(grpcTx.timestamp),
                    fee = Fee(grpcTx.fee),
                    attachment = Attachment(grpcTx.attachment.byteArray()),
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                )
            )
    }

    @Test
    fun `should map to MassTransferTx (with nulls)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = massTransferTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            clearAssetId()
            clearFeeAssetId()
            transfers += transfer {
                recipient = byteString("recipient")
                amount = 100L
            }
            timestamp = 1716881331027L
            fee = 10L
            attachment = byteString("attachment")
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                MassTransferTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    assetId = null,
                    transfers = listOf(
                        Transfer(
                            recipient = Address("recipient".toByteArray()),
                            amount = Amount(100L),
                        )
                    ),
                    feeAssetId = null,
                    timestamp = Timestamp(grpcTx.timestamp),
                    fee = Fee(grpcTx.fee),
                    attachment = Attachment(grpcTx.attachment.byteArray()),
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                )
            )
    }
}
