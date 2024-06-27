package com.wavesenterprise.sdk.node.client.blocking.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import java.time.Duration

class CachingNodeBlockingServiceFactoryBuilder {
    private var txCacheSize: Int? = null
    private var policyItemInfoCacheSize: Int? = null
    private var cacheDuration: Duration? = null

    fun txCacheSize(txCacheSize: Int): CachingNodeBlockingServiceFactoryBuilder =
        this.apply {
            this.txCacheSize = txCacheSize
        }

    fun infoCacheSize(infoCacheSize: Int): CachingNodeBlockingServiceFactoryBuilder =
        this.apply {
            this.policyItemInfoCacheSize = infoCacheSize
        }

    fun cacheDuration(cacheDuration: Duration): CachingNodeBlockingServiceFactoryBuilder =
        this.apply {
            this.cacheDuration = cacheDuration
        }

    fun build(nodeBlockingServiceFactory: NodeBlockingServiceFactory): CachingNodeBlockingServiceFactory {
        val txCache = CaffeineLoadingCache(
            Caffeine.newBuilder()
                .expireAfterWrite(cacheDuration ?: DEFAULT_CACHE_DURATION)
                .initialCapacity(txCacheSize ?: DEFAULT_CACHE_SIZE)
                .build<TxId, TxInfo>(),
        )
        val infoCache = CaffeineLoadingCache(
            Caffeine.newBuilder()
                .expireAfterWrite(cacheDuration ?: DEFAULT_CACHE_DURATION)
                .initialCapacity(policyItemInfoCacheSize ?: DEFAULT_POLICY_ITEM_INFO_CACHE_SIZE)
                .build<String, PolicyItemInfoResponse>(),
        )
        return CachingNodeBlockingServiceFactory(
            nodeBlockingServiceFactory = nodeBlockingServiceFactory,
            txCache = txCache,
            policyItemInfoCache = infoCache,
        )
    }

    companion object {
        @JvmStatic
        fun builder() = CachingNodeBlockingServiceFactoryBuilder()

        const val DEFAULT_CACHE_SIZE = 5000
        const val DEFAULT_POLICY_ITEM_INFO_CACHE_SIZE = 500
        val DEFAULT_CACHE_DURATION: Duration = Duration.ofMinutes(1L)
    }
}
