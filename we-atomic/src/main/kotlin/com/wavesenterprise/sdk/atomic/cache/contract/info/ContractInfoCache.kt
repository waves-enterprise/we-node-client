package com.wavesenterprise.sdk.atomic.cache.contract.info

import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractInfo

interface ContractInfoCache {
    fun put(contractId: ContractId, contractInfo: ContractInfo)
    fun get(contractId: ContractId): ContractInfo?
}
