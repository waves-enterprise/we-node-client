package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.PolicyDataHashTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.transaction.protobuf.atomicBadge
import com.wavesenterprise.transaction.protobuf.policyDataHashTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PolicyDataHashTxMapperTest {

    @Test
    fun `should map to PolicyDataHashTx (all fields)`() {
        val grpcTx = policyDataHashTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("3M1V41oGFr3hLmo3ecNQWPWVHPSfsguWepc")
            dataHash = byteString("8GPtHQeLxhtt8HianM9c8otS2EeAHNVZCfpCRUmYbSFi")
            policyId = byteString("UkvoboGXiwWpASr1GLG9M1MUbhrEMo4NBS7kquxVMw5")
            timestamp = 1716881331027L
            fee = 10L
            feeAssetId = bytesValue {
                value = byteString("3Ge1AptYWH3Xw2jCNkCkYLPECr5q4aSJMXKMSUGL4eEs")
            }
            atomicBadge = atomicBadge {
                trustedSender = bytesValue {
                    value = byteString("3M7EEnszPAT2yr72SgWVDLxfYCa4AYvVRwv")
                }
            }
            proofs += listOf(
                byteString("4NccZyPCgchDjeMdMmFKu7kxyU8AFF4e9cWaPFTQVQyYU1ZCCu3QmtmkfJkrDpDwGs4eJhYUVh5TnwYvjZYKPhLp"),
                byteString("QMGoz6rycNsDLhN3mDce2mqGRQQ8r26vDDw551pnYcAecpFBDA8j38FVqDjLTGuFHs6ScX32fsGcaemmptpCFHk"),
            )
            senderAddress = byteString("3MLti6qku2gD4rhnRcypvFxJE5UYV2P2myM")
        }
        val txVersion = TxVersion.fromInt(3)

        grpcTx
            .domain(txVersion)
            .shouldBe(
                PolicyDataHashTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    dataHash = Hash(grpcTx.dataHash.toByteArray()),
                    policyId = PolicyId(
                        txId = TxId(grpcTx.policyId.toByteArray()),
                    ),
                    timestamp = Timestamp(grpcTx.timestamp),
                    fee = Fee(grpcTx.fee),
                    feeAssetId = FeeAssetId(
                        txId = TxId(grpcTx.feeAssetId.value.toByteArray()),
                    ),
                    atomicBadge = AtomicBadge(
                        trustedSender = Address(grpcTx.atomicBadge.trustedSender.value.toByteArray()),
                    ),
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) }.toList(),
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                    version = txVersion,
                ),
            )
    }

    @Test
    fun `should map to PolicyDataHashTx (with nulls)`() {
        val grpcTx = policyDataHashTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("3M1V41oGFr3hLmo3ecNQWPWVHPSfsguWepc")
            dataHash = byteString("8GPtHQeLxhtt8HianM9c8otS2EeAHNVZCfpCRUmYbSFi")
            policyId = byteString("UkvoboGXiwWpASr1GLG9M1MUbhrEMo4NBS7kquxVMw5")
            timestamp = 1716881331027L
            fee = 10L
            clearFeeAssetId()
            clearAtomicBadge()
            proofs += listOf(
                byteString("4NccZyPCgchDjeMdMmFKu7kxyU8AFF4e9cWaPFTQVQyYU1ZCCu3QmtmkfJkrDpDwGs4eJhYUVh5TnwYvjZYKPhLp"),
                byteString("QMGoz6rycNsDLhN3mDce2mqGRQQ8r26vDDw551pnYcAecpFBDA8j38FVqDjLTGuFHs6ScX32fsGcaemmptpCFHk"),
            )
            senderAddress = byteString("3MLti6qku2gD4rhnRcypvFxJE5UYV2P2myM")
        }
        val txVersion = TxVersion.fromInt(1)

        grpcTx
            .domain(txVersion)
            .shouldBe(
                PolicyDataHashTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    dataHash = Hash(grpcTx.dataHash.toByteArray()),
                    policyId = PolicyId(
                        txId = TxId(grpcTx.policyId.toByteArray()),
                    ),
                    timestamp = Timestamp(grpcTx.timestamp),
                    fee = Fee(grpcTx.fee),
                    feeAssetId = null,
                    atomicBadge = null,
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) }.toList(),
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                    version = txVersion,
                ),
            )
    }
}
