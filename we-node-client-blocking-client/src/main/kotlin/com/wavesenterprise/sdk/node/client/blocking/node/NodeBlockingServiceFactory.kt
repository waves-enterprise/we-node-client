package com.wavesenterprise.sdk.node.client.blocking.node

import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.client.blocking.alias.AliasService
import com.wavesenterprise.sdk.node.client.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.blocking.event.BlockchainEventsService
import com.wavesenterprise.sdk.node.client.blocking.pki.PkiService
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.client.blocking.util.NodeUtilsService

interface NodeBlockingServiceFactory {
    fun addressService(): AddressService
    fun aliasService(): AliasService
    fun blockchainEventsService(): BlockchainEventsService
    fun blocksService(): BlocksService
    fun contractService(): ContractService
    fun nodeInfoService(): NodeInfoService
    fun nodeUtilsService(): NodeUtilsService
    fun pkiService(): PkiService
    fun privacyService(): PrivacyService
    fun txService(): TxService
}
