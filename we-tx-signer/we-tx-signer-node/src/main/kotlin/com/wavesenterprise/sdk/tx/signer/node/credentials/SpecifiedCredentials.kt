package com.wavesenterprise.sdk.tx.signer.node.credentials

interface SpecifiedCredentials {
    fun <T> withCredentials(creds: Credentials, fn: () -> T): T
}
