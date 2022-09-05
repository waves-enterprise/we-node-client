package com.wavesenterprise.sdk.tx.signer.node.credentials

class DefaultSignCredentialsProvider(
    private val credentials: Credentials,
) : SignCredentialsProvider {

    override fun credentials(): Credentials = credentials
}
