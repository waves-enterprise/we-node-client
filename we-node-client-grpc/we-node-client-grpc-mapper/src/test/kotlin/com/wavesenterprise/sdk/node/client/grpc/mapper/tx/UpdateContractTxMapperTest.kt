package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.UpdateContractTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ContractApiVersion
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.MajorVersion
import com.wavesenterprise.sdk.node.domain.MinorVersion
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.ValidationPolicy
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx
import com.wavesenterprise.transaction.protobuf.ValidationPolicyKt.any
import com.wavesenterprise.transaction.protobuf.ValidationPolicyKt.majority
import com.wavesenterprise.transaction.protobuf.ValidationPolicyKt.majorityWithOneOf
import com.wavesenterprise.transaction.protobuf.atomicBadge
import com.wavesenterprise.transaction.protobuf.contractApiVersion
import com.wavesenterprise.transaction.protobuf.docker.updateContractTransaction
import com.wavesenterprise.transaction.protobuf.validationPolicy
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import com.wavesenterprise.transaction.protobuf.ValidationPolicy as ProtoValidationPolicy

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateContractTxMapperTest {

    @ParameterizedTest
    @MethodSource("mappingArguments")
    fun `should map to UpdateContractTx (all fields)`(
        validationPolicyMapping: TestValidationPolicyMapping,
    ) {
        val grpcTx = updateContractTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("3M1V41oGFr3hLmo3ecNQWPWVHPSfsguWepc")
            contractId = byteString("CJrZPGYjjnZp1kLR3a7vCtEoM6Bx7Su2zJ8kU8Zxet4X")
            image = "registry.test-domain.com/test-docker-repo/contract:1.0.1"
            imageHash = "586d70cb288e82198a924871d75849c263c51b205764fa3e51755e54fcde18e8"
            fee = 10L
            timestamp = 1716881331027L
            feeAssetId = bytesValue {
                value = byteString("3Ge1AptYWH3Xw2jCNkCkYLPECr5q4aSJMXKMSUGL4eEs")
            }
            atomicBadge = atomicBadge {
                trustedSender = bytesValue {
                    value = byteString("3M7EEnszPAT2yr72SgWVDLxfYCa4AYvVRwv")
                }
            }
            validationPolicy = validationPolicyMapping.protoValidationPolicy
            apiVersion = contractApiVersion {
                majorVersion = 1
                minorVersion = 0
            }
            proofs += listOf(
                byteString("4NccZyPCgchDjeMdMmFKu7kxyU8AFF4e9cWaPFTQVQyYU1ZCCu3QmtmkfJkrDpDwGs4eJhYUVh5TnwYvjZYKPhLp"),
                byteString("QMGoz6rycNsDLhN3mDce2mqGRQQ8r26vDDw551pnYcAecpFBDA8j38FVqDjLTGuFHs6ScX32fsGcaemmptpCFHk"),
            )
            senderAddress = byteString("3MLti6qku2gD4rhnRcypvFxJE5UYV2P2myM")
        }
        val txVersion = TxVersion.fromInt(4)

        grpcTx
            .domain(txVersion)
            .shouldBe(
                UpdateContractTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    contractId = ContractId(
                        txId = TxId(grpcTx.contractId.toByteArray())
                    ),
                    image = ContractImage(grpcTx.image),
                    imageHash = ContractImageHash(grpcTx.imageHash),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    feeAssetId = FeeAssetId(
                        txId = TxId(grpcTx.feeAssetId.value.toByteArray()),
                    ),
                    atomicBadge = AtomicBadge(
                        trustedSender = Address(grpcTx.atomicBadge.trustedSender.value.toByteArray()),
                    ),
                    validationPolicy = validationPolicyMapping.domainValidationPolicy,
                    apiVersion = ContractApiVersion(
                        major = MajorVersion(grpcTx.apiVersion.majorVersion),
                        minor = MinorVersion(grpcTx.apiVersion.minorVersion),
                    ),
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) }.toList(),
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                    version = txVersion,
                )
            )
    }

    @Test
    fun `should map to UpdateContractTx (with nulls)`() {
        val grpcTx = updateContractTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("3M1V41oGFr3hLmo3ecNQWPWVHPSfsguWepc")
            contractId = byteString("CJrZPGYjjnZp1kLR3a7vCtEoM6Bx7Su2zJ8kU8Zxet4X")
            image = "registry.test-domain.com/test-docker-repo/contract:1.0.1"
            imageHash = "586d70cb288e82198a924871d75849c263c51b205764fa3e51755e54fcde18e8"
            fee = 10L
            timestamp = 1716881331027L
            clearFeeAssetId()
            clearAtomicBadge()
            clearValidationPolicy()
            clearApiVersion()
            proofs += listOf(
                byteString("4NccZyPCgchDjeMdMmFKu7kxyU8AFF4e9cWaPFTQVQyYU1ZCCu3QmtmkfJkrDpDwGs4eJhYUVh5TnwYvjZYKPhLp"),
                byteString("QMGoz6rycNsDLhN3mDce2mqGRQQ8r26vDDw551pnYcAecpFBDA8j38FVqDjLTGuFHs6ScX32fsGcaemmptpCFHk"),
            )
            senderAddress = byteString("3MLti6qku2gD4rhnRcypvFxJE5UYV2P2myM")
        }
        val txVersion = TxVersion.fromInt(2)

        grpcTx
            .domain(txVersion)
            .shouldBe(
                UpdateContractTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    contractId = ContractId(
                        txId = TxId(grpcTx.contractId.toByteArray())
                    ),
                    image = ContractImage(grpcTx.image),
                    imageHash = ContractImageHash(grpcTx.imageHash),
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    feeAssetId = null,
                    atomicBadge = null,
                    validationPolicy = null,
                    apiVersion = null,
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) }.toList(),
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                    version = txVersion,
                )
            )
    }

    private fun mappingArguments(): List<Arguments> =
        TestValidationPolicyMapping.cases().map { testValidationPolicyMapping ->
            arguments(testValidationPolicyMapping)
        }

    data class TestValidationPolicyMapping(
        val protoValidationPolicy: ProtoValidationPolicy,
        val domainValidationPolicy: ValidationPolicy,
    ) {
        companion object {
            fun cases(): List<TestValidationPolicyMapping> =
                listOf(
                    TestValidationPolicyMapping(
                        protoValidationPolicy = validationPolicy {
                            any = any {}
                        },
                        domainValidationPolicy = ValidationPolicy.Any
                    ),
                    TestValidationPolicyMapping(
                        protoValidationPolicy = validationPolicy {
                            majority = majority {}
                        },
                        domainValidationPolicy = ValidationPolicy.Majority
                    ),
                    TestValidationPolicyMapping(
                        protoValidationPolicy = validationPolicy {
                            majorityWithOneOf = majorityWithOneOf {
                                addresses += listOf(
                                    byteString("3M7EEnszPAT2yr72SgWVDLxfYCa4AYvVRwv"),
                                    byteString("3M3xGmJGmxBv2aZ4UFmn93rHxVXTJDKSAnh"),
                                )
                            }
                        },
                        domainValidationPolicy = ValidationPolicy.MajorityWithOneOf(
                            addresses = listOf(
                                Address("3M7EEnszPAT2yr72SgWVDLxfYCa4AYvVRwv".toByteArray()),
                                Address("3M3xGmJGmxBv2aZ4UFmn93rHxVXTJDKSAnh".toByteArray()),
                            )
                        )
                    )
                )
        }
    }
}
