package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.client.blocking.cache.LoadingCache
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId

class PrivacyDataNodesCache(
    private val cache: LoadingCache<PrivacyDataKey, MutableSet<String>>,
) {

    fun get(policyId: PolicyId, policyDataHash: Hash): Set<String> =
        cache.getIfPresent(cacheKey(policyId, policyDataHash)) ?: emptySet()

    fun put(policyId: PolicyId, policyDataHash: Hash, alias: String) {
        val key = cacheKey(policyId, policyDataHash)
        with(cache) {
            getIfPresent(key)?.also {
                put(key, it.apply { add(alias) })
            } ?: put(key, mutableSetOf(alias))
        }
    }

    private fun cacheKey(policyId: PolicyId, policyDataHash: Hash) =
        PrivacyDataKey(
            policyId = policyId,
            dataHash = policyDataHash,
        )
}
