package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.ByteString
import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.CreateContractTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ContractApiVersion
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.DataValue
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
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.contract.ContractName
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.transaction.protobuf.ValidationPolicyKt.any
import com.wavesenterprise.transaction.protobuf.ValidationPolicyKt.majority
import com.wavesenterprise.transaction.protobuf.ValidationPolicyKt.majorityWithOneOf
import com.wavesenterprise.transaction.protobuf.atomicBadge
import com.wavesenterprise.transaction.protobuf.contractApiVersion
import com.wavesenterprise.transaction.protobuf.dataEntry
import com.wavesenterprise.transaction.protobuf.docker.createContractTransaction
import com.wavesenterprise.transaction.protobuf.validationPolicy
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import com.wavesenterprise.transaction.protobuf.DataEntry as ProtoDataEntry
import com.wavesenterprise.transaction.protobuf.ValidationPolicy as ProtoValidationPolicy

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateContractTxMapperTest {

    @ParameterizedTest
    @MethodSource("mappingArguments")
    fun `should map to CreateContractTx (all fields)`(
        paramsMapping: TestParamsMapping,
        validationPolicyMapping: TestValidationPolicyMapping,
    ) {
        val grpcTx = createContractTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("3M1V41oGFr3hLmo3ecNQWPWVHPSfsguWepc")
            image = "registry.test-domain.com/test-docker-repo/contract:1.0.0"
            imageHash = "573387bbf50cfdeda462054b8d85d6c24007f91044501250877392e43ff5ed50"
            contractName = "Test contract name"
            params += paramsMapping.protoParams
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
                CreateContractTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    version = txVersion,
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    image = ContractImage(grpcTx.image),
                    imageHash = ContractImageHash(grpcTx.imageHash),
                    contractName = ContractName(grpcTx.contractName),
                    params = paramsMapping.domainParams,
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
                ),
            )
    }

    @ParameterizedTest
    @MethodSource("mappingArguments")
    fun `should map to CreateContractTx (with nulls)`(
        paramsMapping: TestParamsMapping,
        validationPolicyMapping: TestValidationPolicyMapping,
    ) {
        val grpcTx = createContractTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("3M1V41oGFr3hLmo3ecNQWPWVHPSfsguWepc")
            image = "registry.test-domain.com/test-docker-repo/contract:1.0.0"
            imageHash = "573387bbf50cfdeda462054b8d85d6c24007f91044501250877392e43ff5ed50"
            contractName = "Test contract name"
            params += paramsMapping.protoParams
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
        val txVersion = TxVersion.fromInt(1)

        grpcTx
            .domain(txVersion)
            .shouldBe(
                CreateContractTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    version = txVersion,
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    image = ContractImage(grpcTx.image),
                    imageHash = ContractImageHash(grpcTx.imageHash),
                    contractName = ContractName(grpcTx.contractName),
                    params = paramsMapping.domainParams,
                    fee = Fee(grpcTx.fee),
                    timestamp = Timestamp(grpcTx.timestamp),
                    feeAssetId = null,
                    atomicBadge = null,
                    validationPolicy = null,
                    apiVersion = null,
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) }.toList(),
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                ),
            )
    }

    private fun mappingArguments(): List<Arguments> =
        cartesianProduct(
            TestParamsMapping.cases(),
            TestValidationPolicyMapping.cases(),
        ).map { (testParamsMapping, testValidationPolicyMapping) ->
            arguments(testParamsMapping, testValidationPolicyMapping)
        }

    data class TestParamsMapping(
        val protoParams: List<ProtoDataEntry>,
        val domainParams: List<DataEntry>,
    ) {
        companion object {
            fun cases(): List<TestParamsMapping> =
                listOf(
                    TestParamsMapping(
                        protoParams = listOf(
                            dataEntry {
                                key = "int_0"
                                intValue = 0
                            },
                            dataEntry {
                                key = "int_1"
                                intValue = 1
                            },
                        ),
                        domainParams = listOf(
                            DataEntry(key = DataKey("int_0"), value = DataValue.IntegerDataValue(0)),
                            DataEntry(key = DataKey("int_1"), value = DataValue.IntegerDataValue(1)),
                        ),
                    ),
                    TestParamsMapping(
                        protoParams = listOf(
                            dataEntry {
                                key = "bool_true"
                                boolValue = true
                            },
                            dataEntry {
                                key = "bool_false"
                                boolValue = false
                            },
                        ),
                        domainParams = listOf(
                            DataEntry(key = DataKey("bool_true"), value = DataValue.BooleanDataValue(true)),
                            DataEntry(key = DataKey("bool_false"), value = DataValue.BooleanDataValue(false)),
                        ),
                    ),
                    TestParamsMapping(
                        protoParams = listOf(
                            dataEntry {
                                key = "binary_0"
                                binaryValue = ByteString.copyFrom(byteArrayOf(0))
                            },
                            dataEntry {
                                key = "binary_1"
                                binaryValue = ByteString.copyFrom(byteArrayOf(1))
                            },
                        ),
                        domainParams = listOf(
                            DataEntry(key = DataKey("binary_0"), value = DataValue.BinaryDataValue(byteArrayOf(0))),
                            DataEntry(key = DataKey("binary_1"), value = DataValue.BinaryDataValue(byteArrayOf(1))),
                        ),
                    ),
                    TestParamsMapping(
                        protoParams = listOf(
                            dataEntry {
                                key = "string_1"
                                stringValue = "string_1"
                            },
                            dataEntry {
                                key = "string_2"
                                stringValue = "string_2"
                            },
                        ),
                        domainParams = listOf(
                            DataEntry(key = DataKey("string_1"), value = DataValue.StringDataValue("string_1")),
                            DataEntry(key = DataKey("string_2"), value = DataValue.StringDataValue("string_2")),
                        ),
                    ),
                )
        }
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
                        domainValidationPolicy = ValidationPolicy.Any,
                    ),
                    TestValidationPolicyMapping(
                        protoValidationPolicy = validationPolicy {
                            majority = majority {}
                        },
                        domainValidationPolicy = ValidationPolicy.Majority,
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
                            ),
                        ),
                    ),
                )
        }
    }
}
