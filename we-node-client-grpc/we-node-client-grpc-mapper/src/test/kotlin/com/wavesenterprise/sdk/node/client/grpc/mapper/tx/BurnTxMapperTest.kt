package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.BurnTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.BurnTx
import com.wavesenterprise.transaction.protobuf.assets.burnTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BurnTxMapperTest {

    @Test
    fun `should map to BurnTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = burnTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            chainId = 73
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            assetId = byteString("assetId")
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
                BurnTx(
                    id = TxId(grpcTx.id.byteArray()),
                    chainId = ChainId(grpcTx.chainId.toByte()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    assetId = AssetId(grpcTx.assetId.byteArray()),
                    amount = Amount(grpcTx.amount),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp.fromUtcTimestamp(grpcTx.timestamp),
                    proofs = grpcTx.proofsList.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                ),
            )
    }
}
