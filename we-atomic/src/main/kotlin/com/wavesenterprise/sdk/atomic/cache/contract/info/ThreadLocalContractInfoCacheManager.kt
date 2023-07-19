package com.wavesenterprise.sdk.atomic.cache.contract.info

import com.wavesenterprise.sdk.atomic.manager.ContractInfoCacheManager

class ThreadLocalContractInfoCacheManager : ContractInfoCacheManager {

    companion object {
        private val cacheHolder: ThreadLocal<ContractInfoCache> =
            ThreadLocal.withInitial { DefaultContractInfoCache() }
    }

    override fun getCache(): ContractInfoCache = cacheHolder.get()

    override fun init() {
        cacheHolder.set(DefaultContractInfoCache())
    }

    override fun clear() {
        cacheHolder.remove()
    }
}
