package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.ByteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.GenesisRegisterNodeTxMapper.domain
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.transaction.protobuf.genesisRegisterNodeTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GenesisRegisterNodeTxMapperTest {

    @Test
    fun `should map to GenesisRegisterNodeTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = genesisRegisterNodeTransaction {
            id = ByteString.copyFromUtf8("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            fee = 10L
            timestamp = 1716881331027L
            signature = ByteString.copyFromUtf8(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
        }

        grpcTx.domain(txVersion).apply {
            id.bytes shouldBe grpcTx.id.toByteArray()
            targetPublicKey.bytes shouldBe grpcTx.targetPublicKey.toByteArray()
            fee.value shouldBe grpcTx.fee
            timestamp.utcTimestampMillis shouldBe grpcTx.timestamp
            signature.bytes shouldBe grpcTx.signature.toByteArray()
            version.value shouldBe txVersion.value
        }
    }
}
