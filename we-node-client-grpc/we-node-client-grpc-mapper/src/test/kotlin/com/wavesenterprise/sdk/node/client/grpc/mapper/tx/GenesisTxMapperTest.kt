package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.GenesisTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.transaction.protobuf.genesisTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GenesisTxMapperTest {

    @Test
    fun `should map to GenesisTx (all fields)`() {
        val txId = TxId("id".toByteArray())
        val txAmount = Amount(1000L)
        val txFee = Fee(10L)
        val txTimestamp = Timestamp(1716881331027L)
        val txRecipient = Address("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB".toByteArray())
        val txSignature = Signature(
            "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM".toByteArray()
        )
        val version = TxVersion.fromInt(1)

        val grpcTx = genesisTransaction {
            id = txId.byteString()
            amount = txAmount.value
            fee = txFee.value
            timestamp = txTimestamp.utcTimestampMillis
            recipient = txRecipient.byteString()
            signature = txSignature.byteString()
        }

        grpcTx.domain(version).apply {
            id.bytes shouldBe grpcTx.id.toByteArray()
            amount.value shouldBe grpcTx.amount
            fee.value shouldBe grpcTx.fee
            timestamp.utcTimestampMillis shouldBe grpcTx.timestamp
            recipient.bytes shouldBe grpcTx.recipient.toByteArray()
            signature.bytes shouldBe grpcTx.signature.toByteArray()
        }
    }
}
