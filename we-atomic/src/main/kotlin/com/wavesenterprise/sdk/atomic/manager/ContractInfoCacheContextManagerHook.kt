package com.wavesenterprise.sdk.atomic.manager

class ContractInfoCacheContextManagerHook(
    private val contractInfoCacheManager: ContractInfoCacheManager,
) : AtomicAwareContextManagerHook {

    override fun begin() {
        contractInfoCacheManager.init()
    }

    override fun clear() {
        contractInfoCacheManager.clear()
    }
}
