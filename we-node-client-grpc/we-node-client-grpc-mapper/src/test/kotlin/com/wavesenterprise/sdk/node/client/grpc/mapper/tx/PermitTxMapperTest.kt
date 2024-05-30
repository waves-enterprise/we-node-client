package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.bytesValue
import com.google.protobuf.int64Value
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.PermitTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.PermissionOp
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Role
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.tx.PermitTx
import com.wavesenterprise.transaction.protobuf.acl.permitTransaction
import com.wavesenterprise.transaction.protobuf.atomicBadge
import com.wavesenterprise.transaction.protobuf.permissionOp
import com.wavesenterprise.transaction.protobuf.role
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import com.wavesenterprise.transaction.protobuf.OpType as ProtoOpType
import com.wavesenterprise.transaction.protobuf.PermissionOp as ProtoPermissionOp

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PermitTxMapperTest {

    @ParameterizedTest
    @MethodSource("permissionOpMappingArguments")
    fun `should map to PermitTx (all fields)`(permissionOpMapping: TestPermissionOpMapping) {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = permitTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            target = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            timestamp = 1716881331027L
            fee = 10L
            permissionOp = permissionOpMapping.protoParam
            atomicBadge = atomicBadge {
                trustedSender = bytesValue {
                    byteString("3M7EEnszPAT2yr72SgWVDLxfYCa4AYvVRwv")
                }
            }
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                PermitTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    target = Address(grpcTx.target.byteArray()),
                    timestamp = Timestamp(grpcTx.timestamp),
                    fee = Fee(grpcTx.fee),
                    permissionOp = permissionOpMapping.domainParam,
                    atomicBadge = AtomicBadge(
                        trustedSender = Address(grpcTx.atomicBadge.trustedSender.value.toByteArray()),
                    ),
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                )
            )
    }

    @ParameterizedTest
    @MethodSource("permissionOpMappingArguments")
    fun `should map to PermitTx (with nulls)`(permissionOpMapping: TestPermissionOpMapping) {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = permitTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            target = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
            timestamp = 1716881331027L
            fee = 10L
            permissionOp = permissionOpMapping.protoParam
            clearAtomicBadge()
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                PermitTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    target = Address(grpcTx.target.byteArray()),
                    timestamp = Timestamp(grpcTx.timestamp),
                    fee = Fee(grpcTx.fee),
                    permissionOp = permissionOpMapping.domainParam,
                    atomicBadge = null,
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    version = txVersion,
                )
            )
    }

    private fun permissionOpMappingArguments(): List<TestPermissionOpMapping> {
        val opTypes = OpType.values().toMutableList().also {
            it.remove(OpType.UNKNOWN)
        }
        val roles = Role.values().toList()
        val timestamp = 1716881331027L
        val dueTimestamp = 1716881331027L
        val test = cartesianProduct(opTypes, roles)
        return test.map { (opType, role) ->
            TestPermissionOpMapping(
                protoParam = permissionOp {
                    this.opType = ProtoOpType.valueOf(opType.name)
                    this.role = role {
                        id = role.code
                    }
                    this.timestamp = timestamp
                    this.dueTimestamp = int64Value {
                        value = dueTimestamp
                    }
                },
                domainParam = PermissionOp(
                    opType = opType,
                    role = role,
                    timestamp = Timestamp(1716881331027L),
                    dueTimestamp = Timestamp(1716881331027L),
                ),
            )
        }
    }

    data class TestPermissionOpMapping(
        val protoParam: ProtoPermissionOp,
        val domainParam: PermissionOp,
    )
}
