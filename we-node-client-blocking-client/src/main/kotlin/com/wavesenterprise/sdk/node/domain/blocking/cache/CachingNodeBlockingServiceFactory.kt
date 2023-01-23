package com.wavesenterprise.sdk.node.domain.blocking.cache

import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.domain.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.blocks.BlockAtHeight
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import java.util.Optional

class CachingNodeBlockingServiceFactory(
    private val nodeBlockingServiceFactory: NodeBlockingServiceFactory,
    private val txCache: LoadingCache<TxId, TxInfo>,
    private val policyItemInfoCache: LoadingCache<String, PolicyItemInfoResponse>,
) : NodeBlockingServiceFactory by nodeBlockingServiceFactory {

    override fun blocksService(): BlocksService {
        val blocksService: BlocksService = nodeBlockingServiceFactory.blocksService()
        return object : BlocksService by blocksService {
            private fun cacheTxsInBlock(block: BlockAtHeight) {
                block.transactions.forEach { tx ->
                    txCache.put(tx.id, TxInfo(height = block.height, tx = tx))
                    if (tx is AtomicTx) tx.txs.forEach { atomicInnerTx ->
                        txCache.put(atomicInnerTx.id, TxInfo(height = block.height, tx = atomicInnerTx))
                    }
                }
            }

            override fun blockAtHeight(height: Long): BlockAtHeight =
                blocksService.blockAtHeight(height).also { block ->
                    cacheTxsInBlock(block)
                }

            override fun blockSequence(fromHeight: Long, toHeight: Long): List<BlockAtHeight> =
                blocksService.blockSequence(fromHeight, toHeight).onEach { block ->
                    cacheTxsInBlock(block)
                }
        }
    }

    override fun privacyService(): PrivacyService {
        val privacyService: PrivacyService = nodeBlockingServiceFactory.privacyService()
        return object : PrivacyService by privacyService {
            override fun info(request: PolicyItemRequest): PolicyItemInfoResponse =
                requireNotNull(
                    policyItemInfoCache.load(
                        "${request.policyId.asBase58String()}_${request.dataHash.asHexString()}"
                    ) {
                        privacyService.info(request)
                    }
                )
        }
    }

    override fun txService(): TxService {
        val txService: TxService = nodeBlockingServiceFactory.txService()
        return object : TxService by txService {
            override fun txInfo(txId: TxId): Optional<TxInfo> {
                val txInfo = txCache.getIfPresent(txId)
                return if (txInfo != null) {
                    Optional.of(txInfo)
                } else {
                    txService.txInfo(txId).also {
                        if (it.isPresent) {
                            txCache.put(txId, it.get())
                        }
                    }
                }
            }
        }
    }
}
