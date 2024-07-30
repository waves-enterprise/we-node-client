package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.IssueTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Decimals
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.IssueTxDescription
import com.wavesenterprise.sdk.node.domain.IssueTxName
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Reissuable
import com.wavesenterprise.sdk.node.domain.Script
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.IssueTx
import com.wavesenterprise.transaction.protobuf.assets.issueTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

@Suppress
class IssueTxMapperTest {
    @Test
    fun `should map to IssueTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = issueTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            chainId = 73
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            name = byteString("not_coin")
            description = byteString("description")
            quantity = 1_000_000L
            decimals = 5
            reissuable = false
            fee = 10L
            timestamp = 1716881331027L
            script = BytesValue.of(byteString("script"))
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx.domain(txVersion).apply {
            shouldBe(
                IssueTx(
                    id = TxId(grpcTx.id.byteArray()),
                    chainId = ChainId(grpcTx.chainId.toByte()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    name = IssueTxName(grpcTx.name.byteArray()),
                    description = IssueTxDescription(grpcTx.description.byteArray()),
                    quantity = Quantity(grpcTx.quantity),
                    decimals = Decimals(grpcTx.decimals.toByte()),
                    reissuable = Reissuable(grpcTx.reissuable),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp.fromUtcTimestamp(grpcTx.timestamp),
                    script = Script(grpcTx.script.value.byteArray()),
                    proofs = grpcTx.proofsList.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                ),
            )
        }
    }

    @Test
    fun `should map to IssueTx (with nulls)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = issueTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            chainId = 73
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            name = byteString("not_coin")
            description = byteString("description")
            quantity = 1_000_000L
            decimals = 5
            reissuable = false
            fee = 10L
            timestamp = 1716881331027L
            clearScript()
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx.domain(txVersion).apply {
            shouldBe(
                IssueTx(
                    id = TxId(grpcTx.id.byteArray()),
                    chainId = ChainId(grpcTx.chainId.toByte()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    name = IssueTxName(grpcTx.name.byteArray()),
                    description = IssueTxDescription(grpcTx.description.byteArray()),
                    quantity = Quantity(grpcTx.quantity),
                    decimals = Decimals(grpcTx.decimals.toByte()),
                    reissuable = Reissuable(grpcTx.reissuable),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp.fromUtcTimestamp(grpcTx.timestamp),
                    script = null,
                    proofs = grpcTx.proofsList.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                ),
            )
        }
    }
}
