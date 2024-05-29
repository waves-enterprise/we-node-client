package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.CreateAliasTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Alias
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.CreateAliasTx
import com.wavesenterprise.transaction.protobuf.createAliasTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CreateAliasTxMapperTest {

    @Test
    fun `should map to CreateAliasTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = createAliasTransaction {
            id = ByteString.copyFromUtf8("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = ByteString.copyFromUtf8("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            alias = ByteString.copyFromUtf8("alias")
            fee = 10L
            timestamp = 1716881331027L
            feeAssetId = BytesValue.of(ByteString.copyFromUtf8("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB"))
            proofs += ByteString.copyFromUtf8(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
            senderAddress = ByteString.copyFromUtf8("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx.domain(txVersion).apply {
            shouldBe(
                CreateAliasTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    alias = Alias(grpcTx.alias.toString()),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    feeAssetId = FeeAssetId(TxId(grpcTx.feeAssetId.value.toByteArray())),
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                    version = txVersion,
                )
            )
        }
    }

    @Test
    fun `should map to CreateAliasTx (with nulls)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = createAliasTransaction {
            id = ByteString.copyFromUtf8("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = ByteString.copyFromUtf8("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            alias = ByteString.copyFromUtf8("alias")
            fee = 10L
            timestamp = 1716881331027L
            clearFeeAssetId()
            proofs += ByteString.copyFromUtf8(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
            senderAddress = ByteString.copyFromUtf8("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx.domain(txVersion).apply {
            shouldBe(
                CreateAliasTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    alias = Alias(grpcTx.alias.toString()),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    feeAssetId = null,
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                    version = txVersion,
                )
            )
        }
    }
}
