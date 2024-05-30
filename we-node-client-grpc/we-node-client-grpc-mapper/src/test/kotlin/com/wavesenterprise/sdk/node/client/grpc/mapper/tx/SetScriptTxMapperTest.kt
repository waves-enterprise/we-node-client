package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.SetScriptTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Script
import com.wavesenterprise.sdk.node.domain.ScriptDescription
import com.wavesenterprise.sdk.node.domain.ScriptName
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.SetScriptTx
import com.wavesenterprise.transaction.protobuf.smart.setScriptTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SetScriptTxMapperTest {

    @Test
    fun `should map to SetScriptTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = setScriptTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            chainId = 73
            script = bytesValue {
                value = byteString("script")
            }
            name = byteString("name")
            description = byteString("description")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            fee = 10L
            timestamp = 1716881331027L
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                SetScriptTx(
                    id = TxId(grpcTx.id.byteArray()),
                    chainId = ChainId(grpcTx.chainId.toByte()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    script = Script(grpcTx.script.value.byteArray()),
                    name = ScriptName(grpcTx.name.byteArray()),
                    description = ScriptDescription(grpcTx.description.byteArray()),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                )
            )
    }

    @Test
    fun `should map to SetScriptTx (with nulls)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = setScriptTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            chainId = 73
            clearScript()
            name = byteString("name")
            description = byteString("description")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            fee = 10L
            timestamp = 1716881331027L
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                SetScriptTx(
                    id = TxId(grpcTx.id.byteArray()),
                    chainId = ChainId(grpcTx.chainId.toByte()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    script = null,
                    name = ScriptName(grpcTx.name.byteArray()),
                    description = ScriptDescription(grpcTx.description.byteArray()),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                )
            )
    }
}
