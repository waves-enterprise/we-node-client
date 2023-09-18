package com.wavesenterprise.sdk.node.domain.sign.builder

import com.wavesenterprise.sdk.node.domain.contract.ContractId

interface ContractSignRequestBuilderFactory {

    fun create(contractId: ContractId?): ContractSignRequestBuilder
}
