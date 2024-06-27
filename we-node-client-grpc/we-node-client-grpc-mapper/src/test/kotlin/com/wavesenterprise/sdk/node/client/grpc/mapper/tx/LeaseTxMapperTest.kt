package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.LeaseTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.LeaseTx
import com.wavesenterprise.transaction.protobuf.lease.leaseTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class LeaseTxMapperTest {

    @Test
    fun `should map to LeaseTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = leaseTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            assetId = bytesValue { byteString("assetId") }
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            recipient = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            amount = 1_000_000L
            fee = 10L
            timestamp = 1716881331027L
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                LeaseTx(
                    id = TxId(grpcTx.id.byteArray()),
                    assetId = AssetId(grpcTx.assetId.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    recipient = Address(grpcTx.recipient.byteArray()),
                    amount = Amount(grpcTx.amount),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    proofs = grpcTx.proofsList.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                ),
            )
    }

    @Test
    fun `should map to LeaseTx (with nulls)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = leaseTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            clearAssetId()
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            recipient = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            amount = 1_000_000L
            fee = 10L
            timestamp = 1716881331027L
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                LeaseTx(
                    id = TxId(grpcTx.id.byteArray()),
                    assetId = null,
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    recipient = Address(grpcTx.recipient.byteArray()),
                    amount = Amount(grpcTx.amount),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    proofs = grpcTx.proofsList.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                ),
            )
    }
}
