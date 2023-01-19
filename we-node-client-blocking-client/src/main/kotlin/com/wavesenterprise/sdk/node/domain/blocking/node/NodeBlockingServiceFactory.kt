package com.wavesenterprise.sdk.node.domain.blocking.node

import com.wavesenterprise.sdk.node.domain.blocking.address.AddressService
import com.wavesenterprise.sdk.node.domain.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.domain.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.domain.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService

interface NodeBlockingServiceFactory {
    fun txService(): TxService
    fun contractService(): ContractService
    fun addressService(): AddressService
    fun nodeInfoService(): NodeInfoService
    fun privacyService(): PrivacyService
    fun blocksService(): BlocksService
}
