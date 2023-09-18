package com.wavesenterprise.sdk.node.client.blocking

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.BlockVersion
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataSize
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FileName
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxCount
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.blocks.BlockAtHeight
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import com.wavesenterprise.sdk.node.domain.privacy.Data
import com.wavesenterprise.sdk.node.domain.privacy.DataAuthor
import com.wavesenterprise.sdk.node.domain.privacy.DataComment
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemFileInfo
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.tx.AtomicInnerTx
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import com.wavesenterprise.sdk.node.domain.tx.UtxSize
import java.util.UUID

fun policyDataHashTx() = PolicyDataHashTx(
    id = TxId("".toByteArray()),
    senderPublicKey = PublicKey.fromBase58(""),
    dataHash = Hash("".toByteArray()),
    policyId = PolicyId.fromBase58(""),
    timestamp = Timestamp(1),
    fee = Fee(1),
    senderAddress = Address("".toByteArray()),
    version = TxVersion(1),
)

fun policyItemInfoResponse() = PolicyItemInfoResponse(
    senderAddress = Address("".toByteArray()),
    policyId = PolicyId.fromBase58(""),
    info = PolicyItemFileInfo(
        filename = FileName(""),
        size = DataSize(1),
        timestamp = Timestamp(1),
        author = DataAuthor(""),
        comment = DataComment(""),
    ),
    dataHash = Hash("".toByteArray()),
)

fun sendDataRequest() = SendDataRequest(
    senderAddress = Address.fromBase58("B"),
    policyId = PolicyId.fromBase58("AB"),
    data = Data("".toByteArray()),
    fee = Fee(0),
    dataHash = Hash("".toByteArray()),
    info = PolicyItemFileInfo(
        filename = FileName(""),
        size = DataSize(1),
        timestamp = Timestamp(1),
        author = DataAuthor(""),
        comment = DataComment(""),
    ),
    broadcastTx = true,
)

fun utxSize(
    txCount: TxCount = txCount(),
    size: DataSize = dataSize(),
) = UtxSize(
    txCount = txCount,
    size = size,
)

fun txCount(
    value: Int = 0,
) = TxCount(
    value = value,
)

fun dataSize(
    bytesCount: Long = 0,
) = DataSize(
    bytesCount = bytesCount,
)

fun txInfo(
    height: Long = 11111L,
    tx: Tx = callContractTx(),
) = TxInfo(
    height = Height(height),
    tx = tx,
)

fun callContractTx(
    id: TxId = TxId(UUID.randomUUID().toString().toByteArray()),
    params: List<DataEntry> = listOf(),
    senderAddress: Address = Address("address".toByteArray()),
) = CallContractTx(
    id = id,
    senderPublicKey = PublicKey(ByteArray(1)),
    contractId = ContractId(TxId(ByteArray(1))),
    params = params,
    fee = Fee(1L),
    version = TxVersion(1),
    proofs = null,
    timestamp = Timestamp(1L),
    feeAssetId = null,
    contractVersion = ContractVersion(1),
    atomicBadge = null,
    senderAddress = senderAddress,
)

fun atomicTx(
    txId: TxId = TxId(UUID.randomUUID().toString().toByteArray()),
    innerTxs: List<AtomicInnerTx> = listOf(),
    senderAddress: Address = Address("address".toByteArray()),
) = AtomicTx(
    id = txId,
    senderPublicKey = PublicKey("publicKey".toByteArray()),
    txs = innerTxs,
    senderAddress = senderAddress,
    timestamp = Timestamp(1L),
    fee = Fee(0L),
    version = TxVersion(1),
)

fun blockAtHeight(
    txs: List<Tx>,
) = BlockAtHeight(
    reference = "reference",
    blockSize = 1L,
    features = listOf(),
    signature = Signature("signature".toByteArray()),
    fee = Fee(1L),
    generator = "generator",
    transactionCount = 2L,
    transactions = txs,
    version = BlockVersion(2),
    timestamp = 1L,
    height = Height(11111L),
)

fun policyItemRequest(
    txId: TxId = TxId(UUID.randomUUID().toString().toByteArray()),
    hash: Hash = Hash("hash".toByteArray())
) = PolicyItemRequest(
    policyId = PolicyId(txId = txId),
    dataHash = hash,
)
