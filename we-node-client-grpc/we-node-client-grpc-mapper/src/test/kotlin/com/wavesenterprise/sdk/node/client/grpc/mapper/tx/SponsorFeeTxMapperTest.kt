package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.SponsorFeeTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Enabled
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.SponsorFeeTx
import com.wavesenterprise.transaction.protobuf.assets.sponsorFeeTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SponsorFeeTxMapperTest {

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should map to SponsorFeeTx (all fields)`(sponsorshipIsEnabled: Boolean) {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = sponsorFeeTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            assetId = byteString("assetId")
            isEnabled = sponsorshipIsEnabled
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
                SponsorFeeTx(
                    id = TxId(grpcTx.id.byteArray()),
                    assetId = AssetId(grpcTx.assetId.byteArray()),
                    enabled = Enabled(grpcTx.isEnabled),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    proofs = grpcTx.proofsList.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                )
            )
    }
}
