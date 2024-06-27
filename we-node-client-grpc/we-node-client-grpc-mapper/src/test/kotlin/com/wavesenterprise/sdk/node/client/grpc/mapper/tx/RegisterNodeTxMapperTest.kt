package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.ByteString
import com.google.protobuf.StringValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.RegisterNodeTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.NodeName
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.RegisterNodeTx
import com.wavesenterprise.transaction.protobuf.registerNodeTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import com.wavesenterprise.transaction.protobuf.OpType as ProtoOpType

class RegisterNodeTxMapperTest {

    @Test
    fun `should map to RegisterNodeTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = registerNodeTransaction {
            id = ByteString.copyFromUtf8("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = ByteString.copyFromUtf8("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            target = ByteString.copyFromUtf8("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            nodeName = StringValue.of("node-0")
            opType = ProtoOpType.ADD
            timestamp = 1716881331027L
            fee = 10L
            senderAddress = ByteString.copyFromUtf8("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            proofs += ByteString.copyFromUtf8(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
        }
        grpcTx.domain(txVersion).apply {
            shouldBe(
                RegisterNodeTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    targetPublicKey = PublicKey(grpcTx.target.toByteArray()),
                    nodeName = NodeName(grpcTx.nodeName.value),
                    opType = OpType.valueOf(grpcTx.opType.name),
                    timestamp = Timestamp(grpcTx.timestamp),
                    fee = Fee(grpcTx.fee),
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) },
                    version = txVersion,
                ),
            )
        }
    }

    @Test
    fun `should map to RegisterNodeTx (with nulls)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = registerNodeTransaction {
            id = ByteString.copyFromUtf8("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = ByteString.copyFromUtf8("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            target = ByteString.copyFromUtf8("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            clearNodeName()
            opType = ProtoOpType.ADD
            timestamp = 1716881331027L
            fee = 10L
            senderAddress = ByteString.copyFromUtf8("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            proofs += ByteString.copyFromUtf8(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
        }

        grpcTx.domain(txVersion).apply {
            shouldBe(
                RegisterNodeTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    targetPublicKey = PublicKey(grpcTx.target.toByteArray()),
                    nodeName = null,
                    opType = OpType.valueOf(grpcTx.opType.name),
                    timestamp = Timestamp(grpcTx.timestamp),
                    fee = Fee(grpcTx.fee),
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) },
                    version = txVersion,
                ),
            )
        }
    }
}
