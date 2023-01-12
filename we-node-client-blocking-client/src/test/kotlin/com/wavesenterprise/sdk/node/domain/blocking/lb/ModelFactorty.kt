package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataSize
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FileName
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.privacy.Data
import com.wavesenterprise.sdk.node.domain.privacy.DataAuthor
import com.wavesenterprise.sdk.node.domain.privacy.DataComment
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemFileInfo
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx

fun nodeIdentity(
    nodeCredentials: NodeCredentials = nodeCredentials(),
    nodeAlias: String = "nodeAlias",
) = NodeIdentity(
    credentials = nodeCredentials,
    nodeAlias = nodeAlias,
)

fun nodeCredentials(
    address: Address = Address.fromBase58("address"),
    password: Password = Password.fromString("password"),
) = NodeCredentials(
    address = address,
    keyStorePassword = password,
)

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
