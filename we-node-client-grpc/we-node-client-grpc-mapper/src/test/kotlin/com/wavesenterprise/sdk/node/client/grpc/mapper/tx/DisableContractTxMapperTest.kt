package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.ByteString
import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.CreateAliasTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.DisableContractTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.tx.DisableContractTx
import com.wavesenterprise.transaction.protobuf.atomicBadge
import com.wavesenterprise.transaction.protobuf.docker.disableContractTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class DisableContractTxMapperTest {

    @Test
    fun `should map to DisableContractTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = disableContractTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            contractId = byteString("contractId")
            fee = 10L
            timestamp = 1716881331027L
            feeAssetId = bytesValue {
                value = byteString("feeAssetId")
            }
            atomicBadge = atomicBadge {
                trustedSender = bytesValue {
                    value = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
                }
            }
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
            senderAddress = ByteString.copyFromUtf8("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                DisableContractTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    contractId = ContractId(
                        txId = TxId(grpcTx.contractId.byteArray()),
                    ),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    feeAssetId = FeeAssetId(
                        txId = TxId(grpcTx.feeAssetId.value.byteArray()),
                    ),
                    atomicBadge = AtomicBadge(
                        trustedSender = Address(grpcTx.atomicBadge.trustedSender.value.byteArray()),
                    ),
                    proofs = grpcTx.proofsList?.map {
                        Signature(it.byteArray())
                    },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                ),
            )
    }

    @Test
    fun `should map to DisableContractTx (with nulls)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = disableContractTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            contractId = byteString("contractId")
            fee = 10L
            timestamp = 1716881331027L
            clearFeeAssetId()
            clearAtomicBadge()
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM",
            )
            senderAddress = ByteString.copyFromUtf8("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                DisableContractTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    contractId = ContractId(
                        txId = TxId(grpcTx.contractId.byteArray()),
                    ),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    feeAssetId = null,
                    atomicBadge = null,
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                    version = txVersion,
                ),
            )
    }
}
