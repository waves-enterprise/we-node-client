package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.RoleMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.GenesisPermitTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Role
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.transaction.protobuf.genesisPermitTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GenesisPermitTxMapperTest {

    @Test
    fun `should map to GenesisPermitTx (all fields)`() {
        val txId = TxId.fromBase58("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
        val txTarget = Address.fromBase58("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        val txRole = Role.SENDER
        val txFee = Fee(10L)
        val txTimestamp = Timestamp(1716881331027L)
        val txSignature = Signature(
            "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM".toByteArray()
        )
        val txVersion = TxVersion.fromInt(1)

        val grpcTx = genesisPermitTransaction {
            id = txId.byteString()
            target = txTarget.byteString()
            role = txRole.dto()
            fee = txFee.value
            timestamp = txTimestamp.utcTimestampMillis
            signature = txSignature.byteString()
        }

        grpcTx.domain(txVersion).apply {
            id.bytes shouldBe grpcTx.id.toByteArray()
            target.bytes shouldBe grpcTx.target.toByteArray()
            role.code shouldBe grpcTx.role.id
            fee.value shouldBe grpcTx.fee
            timestamp.utcTimestampMillis shouldBe grpcTx.timestamp
            signature.bytes shouldBe grpcTx.signature.toByteArray()
            version.value shouldBe txVersion.value
        }
    }
}
