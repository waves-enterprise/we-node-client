package com.wavesenterprise.sdk.tx.signer.node.credentials

class ThreadLocalSignCredentialsProvider : SignCredentialsProvider, SpecifiedCredentials {
    private val threadLocal: ThreadLocal<Credentials> = ThreadLocal()

    override fun <T> withCredentials(creds: Credentials, fn: () -> T): T =
        try {
            threadLocal.set(creds)
            fn()
        } finally {
            threadLocal.remove()
        }

    override fun credentials(): Credentials = requireNotNull(threadLocal.get()) { "Credentials haven't been set" }
}
