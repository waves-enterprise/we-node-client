package com.wavesenterprise.sdk.node.domain.blocking.cache

interface LoadingCache<K, V : Any> {
    fun load(key: K, loader: () -> V?): V?
    fun put(key: K, value: V)
    fun getIfPresent(key: K): V?
}
