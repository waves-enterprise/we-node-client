package com.wavesenterprise.sdk.node.client.blocking.node

import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.client.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.blocking.event.BlockchainEventsService
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.client.blocking.util.NodeUtilsService

interface NodeBlockingServiceFactory {
    fun txService(): TxService
    fun contractService(): ContractService
    fun addressService(): AddressService
    fun nodeInfoService(): NodeInfoService
    fun privacyService(): PrivacyService
    fun blocksService(): BlocksService
    fun blockchainEventsService(): BlockchainEventsService
    fun nodeUtilsService(): NodeUtilsService
}
