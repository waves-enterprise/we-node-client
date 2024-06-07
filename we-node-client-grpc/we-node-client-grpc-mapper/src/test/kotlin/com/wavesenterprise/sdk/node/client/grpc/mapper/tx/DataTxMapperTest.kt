package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.DataEntryMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.DataTxMapper.domain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.DataValue
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.DataTx
import com.wavesenterprise.transaction.protobuf.dataEntry
import com.wavesenterprise.transaction.protobuf.dataTransaction
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class DataTxMapperTest {

    @Test
    fun `should map to DataTx (all fields)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = dataTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            authorPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            data += listOf(
                dataEntry {
                    key = "key"
                    stringValue = "value"
                },
                dataEntry {
                    key = "key"
                    boolValue = true
                },
                dataEntry {
                    key = "key"
                    intValue = 1
                },
                dataEntry {
                    key = "key"
                    binaryValue = byteString("binaryValue")
                },
            )
            fee = 10L
            feeAssetId = bytesValue {
                value = byteString("feeAssetId")
            }
            timestamp = 1716881331027L
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                DataTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    authorPublicKey = PublicKey(grpcTx.authorPublicKey.byteArray()),
                    data = listOf(
                        DataEntry(
                            key = DataKey("key"),
                            value = DataValue.StringDataValue("value")
                        ),
                        DataEntry(
                            key = DataKey("key"),
                            value = DataValue.BooleanDataValue(true)
                        ),
                        DataEntry(
                            key = DataKey("key"),
                            value = DataValue.IntegerDataValue(1)
                        ),
                        DataEntry(
                            key = DataKey("key"),
                            value = DataValue.BinaryDataValue("binaryValue".toByteArray())
                        ),
                    ),
                    fee = Fee(grpcTx.fee),
                    feeAssetId = FeeAssetId(txId = TxId(grpcTx.feeAssetId.value.byteArray())),
                    timestamp = Timestamp(grpcTx.timestamp),
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    authorAddress = Address(grpcTx.authorPublicKey.byteArray()),
                    version = txVersion,
                )
            )
    }

    @Test
    fun `should map to DataTx (with nulls)`() {
        val txVersion = TxVersion.fromInt(1)
        val grpcTx = dataTransaction {
            id = byteString("DnK5Xfi2wXUJx9BjK9X6ZpFdTLdq2GtWH9pWrcxcmrhB")
            senderPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            authorPublicKey = byteString("C4eRfdUFaZMRkfUp91bYr7uMgdBRnUfAxuAjetxmK7KY")
            data += listOf(
                dataEntry {
                    key = "key"
                    stringValue = "value"
                },
                dataEntry {
                    key = "key"
                    boolValue = true
                },
                dataEntry {
                    key = "key"
                    intValue = 1
                },
                dataEntry {
                    key = "key"
                    binaryValue = byteString("binaryValue")
                },
            )
            fee = 10L
            clearFeeAssetId()
            timestamp = 1716881331027L
            proofs += byteString(
                "2Gns72hraH5yay3eiWeyHQEA1wTqiiAztaLjHinEYX91FEv62HFW38Hq89GnsEJFHUvo9KHYtBBrb8hgTA9wN7DM"
            )
            senderAddress = byteString("3N9vL3apA4j2L5PojHW8TYmfHx9Lo2ZaKPB")
        }
        grpcTx
            .domain(txVersion)
            .shouldBe(
                DataTx(
                    id = TxId(grpcTx.id.byteArray()),
                    senderPublicKey = PublicKey(grpcTx.senderPublicKey.byteArray()),
                    authorPublicKey = PublicKey(grpcTx.authorPublicKey.byteArray()),
                    data = listOf(
                        DataEntry(
                            key = DataKey("key"),
                            value = DataValue.StringDataValue("value")
                        ),
                        DataEntry(
                            key = DataKey("key"),
                            value = DataValue.BooleanDataValue(true)
                        ),
                        DataEntry(
                            key = DataKey("key"),
                            value = DataValue.IntegerDataValue(1)
                        ),
                        DataEntry(
                            key = DataKey("key"),
                            value = DataValue.BinaryDataValue("binaryValue".toByteArray())
                        ),
                    ),
                    fee = Fee(grpcTx.fee),
                    feeAssetId = null,
                    timestamp = Timestamp(grpcTx.timestamp),
                    proofs = grpcTx.proofsList?.map { Signature(it.byteArray()) },
                    senderAddress = Address(grpcTx.senderAddress.byteArray()),
                    authorAddress = Address(grpcTx.authorPublicKey.byteArray()),
                    version = txVersion,
                )
            )
    }
}
