package com.wavesenterprise.sdk.node.domain.blocking.node

import com.wavesenterprise.sdk.node.domain.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService

interface NodeBlockingServiceFactory {
    fun txService(): TxService
    fun contractService(): ContractService
}
