package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.ByteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.ExecutedContractTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.DataValue
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.ValidationProof
import com.wavesenterprise.sdk.node.domain.tx.ExecutableTx
import com.wavesenterprise.sdk.node.domain.tx.ExecutedContractTx
import com.wavesenterprise.transaction.protobuf.ExecutableTransaction
import com.wavesenterprise.transaction.protobuf.dataEntry
import com.wavesenterprise.transaction.protobuf.docker.executedContractTransaction
import com.wavesenterprise.transaction.protobuf.validationProof
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import com.wavesenterprise.transaction.protobuf.DataEntry as ProtoDataEntry

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExecutedContractTxMapperTest {

    @BeforeEach
    fun beforeEach() {
        clearAllMocks()
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @ParameterizedTest
    @MethodSource("mappingArguments")
    fun `should map to ExecutedContractTx (all fields)`(
        resultsMapping: TestResultsMapping,
    ) {
        val (grpcExecutableTx, domainExecutableTx) = mockk<ExecutableTransaction>() to mockk<ExecutableTx>()
        mockkStatic(ExecutableTxMapper::domainInternal)
        every { ExecutableTxMapper.domainInternal(grpcExecutableTx) } returns domainExecutableTx

        val grpcTx = executedContractTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("3M1V41oGFr3hLmo3ecNQWPWVHPSfsguWepc")
            tx = grpcExecutableTx
            results += resultsMapping.protoResults
            resultsHash = byteString("3u9ApJCJEX3AoYtKfachMZGfxrLDGaD6FvMrq8EnjPaq")
            validationProofs += listOf(
                validationProof {
                    validatorPublicKey = byteString("3M1V41oGFr3hLmo3ecNQWPWVHPSfsguWepc")
                    signature =
                        byteString("2hRxJ2876CdJ498UCpErNfDSYdt2mTK4XUnmZNgZiq63RupJs5WTrAqR46c4rLQdq4toBZk2tSYCeAQWEQyi72U6")
                },
                validationProof {
                    validatorPublicKey = byteString("4WnvQPit2Di1iYXDgDcXnJZ5yroKW54vauNoxdNeMi2g")
                    signature =
                        byteString("5ahD78wciu8YTsLoxo1XRghJWAGG7At7ePiBWTNzdkvX7cViRCKRLjjjPTGCoAH2mdGQK9i1JiY1wh18eh4h7pGy")
                },
            )
            timestamp = 1716881331027L
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
                ExecutedContractTx(
                    id = TxId(grpcTx.id.toByteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.toByteArray()),
                    tx = domainExecutableTx,
                    results = resultsMapping.domainResults,
                    resultsHash = Hash(grpcTx.resultsHash.toByteArray()),
                    validationProofs = grpcTx.validationProofsList.map {
                        ValidationProof(
                            validatorPublicKey = PublicKey(it.validatorPublicKey.toByteArray()),
                            signature = Signature(it.signature.toByteArray()),
                        )
                    },
                    timestamp = Timestamp(grpcTx.timestamp),
                    atomicBadge = null,
                    proofs = grpcTx.proofsList.map { Signature(it.toByteArray()) }.toList(),
                    senderAddress = Address(grpcTx.senderAddress.toByteArray()),
                    version = txVersion,
                )
            )
    }

    private fun mappingArguments(): List<Arguments> =
        TestResultsMapping.cases().map { testResultsMapping ->
            arguments(testResultsMapping)
        }

    data class TestResultsMapping(
        val protoResults: List<ProtoDataEntry>,
        val domainResults: List<DataEntry>,
    ) {
        companion object {
            fun cases(): List<TestResultsMapping> =
                listOf(
                    TestResultsMapping(
                        protoResults = listOf(
                            dataEntry { key = "int_0"; intValue = 0 },
                            dataEntry { key = "int_1"; intValue = 1 },
                        ),
                        domainResults = listOf(
                            DataEntry(key = DataKey("int_0"), value = DataValue.IntegerDataValue(0)),
                            DataEntry(key = DataKey("int_1"), value = DataValue.IntegerDataValue(1)),
                        )
                    ),
                    TestResultsMapping(
                        protoResults = listOf(
                            dataEntry { key = "bool_true"; boolValue = true },
                            dataEntry { key = "bool_false"; boolValue = false },
                        ),
                        domainResults = listOf(
                            DataEntry(key = DataKey("bool_true"), value = DataValue.BooleanDataValue(true)),
                            DataEntry(key = DataKey("bool_false"), value = DataValue.BooleanDataValue(false)),
                        )
                    ),
                    TestResultsMapping(
                        protoResults = listOf(
                            dataEntry { key = "binary_0"; binaryValue = ByteString.copyFrom(byteArrayOf(0)) },
                            dataEntry { key = "binary_1"; binaryValue = ByteString.copyFrom(byteArrayOf(1)) }
                        ),
                        domainResults = listOf(
                            DataEntry(key = DataKey("binary_0"), value = DataValue.BinaryDataValue(byteArrayOf(0))),
                            DataEntry(key = DataKey("binary_1"), value = DataValue.BinaryDataValue(byteArrayOf(1))),
                        )
                    ),
                    TestResultsMapping(
                        protoResults = listOf(
                            dataEntry { key = "string_1"; stringValue = "string_1" },
                            dataEntry { key = "string_2"; stringValue = "string_2" },
                        ),
                        domainResults = listOf(
                            DataEntry(key = DataKey("string_1"), value = DataValue.StringDataValue("string_1")),
                            DataEntry(key = DataKey("string_2"), value = DataValue.StringDataValue("string_2")),
                        )
                    ),
                )
        }
    }
}
