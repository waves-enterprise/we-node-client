package com.wavesenterprise.sdk.atomic.cache.contract.info

import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractInfo

class DefaultContractInfoCache : ContractInfoCache {

    private val contractInfos: MutableMap<ContractId, ContractInfo> = mutableMapOf()

    override fun put(contractId: ContractId, contractInfo: ContractInfo) {
        contractInfos[contractId] = contractInfo
    }
    override fun get(contractId: ContractId): ContractInfo? = contractInfos[contractId]
}
