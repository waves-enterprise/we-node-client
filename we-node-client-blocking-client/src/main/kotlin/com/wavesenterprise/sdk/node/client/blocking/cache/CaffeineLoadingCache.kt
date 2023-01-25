package com.wavesenterprise.sdk.node.client.blocking.cache

import com.github.benmanes.caffeine.cache.Cache

class CaffeineLoadingCache<K : Any, V : Any>(
    private val cache: Cache<K, V>,
) : LoadingCache<K, V> {

    override fun load(key: K, loader: () -> V?): V? {
        return cache.getIfPresent(key) ?: loader()?.also { value ->
            cache.put(key, value)
        }
    }

    override fun put(key: K, value: V) {
        cache.put(key, value)
    }

    override fun getIfPresent(key: K): V? = cache.getIfPresent(key)
}
