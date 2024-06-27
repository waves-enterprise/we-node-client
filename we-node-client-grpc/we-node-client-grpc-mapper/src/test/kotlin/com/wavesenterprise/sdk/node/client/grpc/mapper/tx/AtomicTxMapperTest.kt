package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.AtomicTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.SetScriptTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.transaction.protobuf.atomicTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AtomicTxMapperTest {

    @Test
    fun `should map to AtomicTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = atomicTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            miner = bytesValue {
                byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            }
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            timestamp = 1716881331027L
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                AtomicTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    miner = Address(grpcTx.miner.value.byteArray()),
                    txs = listOf(),
                    fee = Fee(0),
                    timestamp = Timestamp(grpcTx.timestamp),
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                ),
            )
    }

    @Test
    fun `should map to AtomicTx (with nulls)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = atomicTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            clearMiner()
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            timestamp = 1716881331027L
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                AtomicTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    miner = null,
                    txs = listOf(),
                    fee = Fee(0),
                    timestamp = Timestamp(grpcTx.timestamp),
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                ),
            )
    }
}
