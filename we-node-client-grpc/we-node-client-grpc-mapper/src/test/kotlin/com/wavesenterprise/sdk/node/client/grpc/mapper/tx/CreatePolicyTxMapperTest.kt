package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.CreatePolicyTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PolicyDescription
import com.wavesenterprise.sdk.node.domain.PolicyName
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.tx.CreatePolicyTx
import com.wavesenterprise.transaction.protobuf.atomicBadge
import com.wavesenterprise.transaction.protobuf.createPolicyTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CreatePolicyTxMapperTest {

    @Test
    fun `should map to CreatePolicyTx (all fields)`() {
        val grpcTx = createPolicyTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("3M1V41oGFr3hLmo3ecNQWPWVHPSfsguWepc")
            policyName = "TEST_POLICY"
            description = "TEST_POLICY description"
            recipients += listOf(
                byteString("3MLti6qku2gD4rhnRcypvFxJE5UYV2P2myM"),
                byteString("3MNP3Cea3epMQsRNya66UEh5uCq9wiuw1fn"),
            )
            owners += listOf(
                byteString("3MLti6qku2gD4rhnRcypvFxJE5UYV2P2myM"),
                byteString("3MNP3Cea3epMQsRNya66UEh5uCq9wiuw1fn"),
                byteString("3M7EEnszPAT2yr72SgWVDLxfYCa4AYvVRwv"),
            )
            timestamp = 1716881331027L
            fee = 10L
            feeAssetId = bytesValue {
                byteString("3Ge1AptYWH3Xw2jCNkCkYLPECr5q4aSJMXKMSUGL4eEs")
            }
            atomicBadge = atomicBadge {
                trustedSender = bytesValue {
                    byteString("3M7EEnszPAT2yr72SgWVDLxfYCa4AYvVRwv")
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
                CreatePolicyTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    policyName = PolicyName(grpcTx.policyName),
                    description = PolicyDescription(grpcTx.description),
                    recipients = grpcTx.recipientsList.map { Address(it.toByteArray()) }.toList(),
                    owners = grpcTx.ownersList.map { Address(it.toByteArray()) }.toList(),
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
    fun `should map to CreatePolicyTx (with nulls)`() {
        val grpcTx = createPolicyTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("3M1V41oGFr3hLmo3ecNQWPWVHPSfsguWepc")
            policyName = "TEST_POLICY"
            description = "TEST_POLICY description"
            recipients += listOf(
                byteString("3MLti6qku2gD4rhnRcypvFxJE5UYV2P2myM"),
                byteString("3MNP3Cea3epMQsRNya66UEh5uCq9wiuw1fn"),
            )
            owners += listOf(
                byteString("3MLti6qku2gD4rhnRcypvFxJE5UYV2P2myM"),
                byteString("3MNP3Cea3epMQsRNya66UEh5uCq9wiuw1fn"),
                byteString("3M7EEnszPAT2yr72SgWVDLxfYCa4AYvVRwv"),
            )
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
                CreatePolicyTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    policyName = PolicyName(grpcTx.policyName),
                    description = PolicyDescription(grpcTx.description),
                    recipients = grpcTx.recipientsList.map { Address(it.toByteArray()) }.toList(),
                    owners = grpcTx.ownersList.map { Address(it.toByteArray()) }.toList(),
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
