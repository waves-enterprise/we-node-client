package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes

data class PermitDataEntry(
    val key: DataKey,
    val action: PermitDataAction,
) : SerializableToBytes {
    override fun getSignatureBytes(networkByte: Byte?): ByteArray =
        DataEntry(
            key = key,
            value = (action as PermitDataAction.SetDataAction).value, // TODO: https://jira.web3tech.ru/browse/WTCH-212
        ).getSignatureBytes()
}
