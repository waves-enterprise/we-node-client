package com.wavesenterprise.sdk.atomic.manager

import com.wavesenterprise.sdk.atomic.cache.contract.info.ContractInfoCache

interface ContractInfoCacheManager {

    fun getCache(): ContractInfoCache

    fun init()

    fun clear()
}
