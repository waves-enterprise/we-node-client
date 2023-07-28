package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.atomic.manager.AtomicAwareContextManager
import com.wavesenterprise.sdk.atomic.manager.ContractInfoCacheManager
import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractInfo
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion.Companion.update
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.sign.AtomicInnerSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.ContractTx.Companion.contractId
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.sdk.node.domain.tx.ExecutableTx
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx
import com.wavesenterprise.sdk.tx.signer.TxSigner
import java.util.Optional

class AtomicAwareNodeBlockingServiceFactory(
    private val nodeBlockingServiceFactory: NodeBlockingServiceFactory,
    private val atomicAwareContextManager: AtomicAwareContextManager,
    private val contractInfoCacheManager: ContractInfoCacheManager,
    private val txSigner: () -> TxSigner,
) : NodeBlockingServiceFactory by nodeBlockingServiceFactory {

    private val txSignerFromContext: TxSigner by lazy { txSigner.invoke() }

    override fun txService(): TxService = nodeBlockingServiceFactory.txService().let { txService ->
        object : TxService by txService {
            override fun <T : Tx> broadcast(tx: T): T =
                with(atomicAwareContextManager.getContext()) {
                    if (isSentInAtomic() && tx !is AtomicTx) {
                        tx.also { tx ->
                            addTx(tx)
                            if (tx is ExecutableTx) cacheContractInfo(tx)
                        }
                    } else {
                        txService.broadcast(tx)
                    }
                }

            override fun <T : Tx> signAndBroadcast(request: SignRequest<T>): T =
                with(atomicAwareContextManager.getContext()) {
                    if (isSentInAtomic() && request is AtomicInnerSignRequest) {
                        txSignerFromContext.sign(request).also { tx ->
                            addTx(tx)
                            if (tx is ExecutableTx) cacheContractInfo(tx)
                        }
                    } else {
                        txService.signAndBroadcast(request)
                    }
                }

            private fun cacheContractInfo(tx: ExecutableTx) {
                when (tx) {
                    is CreateContractTx -> ContractInfo(
                        id = tx.contractId(),
                        image = tx.image,
                        imageHash = tx.imageHash,
                        version = ContractVersion(1),
                        active = true,
                    )

                    is UpdateContractTx ->
                        nodeBlockingServiceFactory
                            .contractService()
                            .getContractInfo(tx.contractId)
                            .get()
                            .run {
                                this.copy(
                                    version = this.version.update()
                                )
                            }

                    is CallContractTx -> null
                }?.also {
                    contractInfoCacheManager.getCache().put(
                        contractId = it.id,
                        contractInfo = it,
                    )
                }
            }
        }
    }

    override fun privacyService(): PrivacyService = nodeBlockingServiceFactory.privacyService().let { privacyService ->
        object : PrivacyService by privacyService {
            override fun sendData(request: SendDataRequest): PolicyDataHashTx =
                privacyService.sendData(request.withAtomicBadgeIfNecessary())

            private fun SendDataRequest.withAtomicBadgeIfNecessary() =
                withAtomicBadge(
                    atomicBadge = if (!broadcastTx && senderAddress != txSignerFromContext.getSignerAddress())
                        AtomicBadge(
                            trustedSender = txSignerFromContext.getSignerAddress(),
                        ) else null
                )
        }
    }

    override fun contractService(): ContractService =
        nodeBlockingServiceFactory.contractService().let { contractService ->
            object : ContractService by contractService {
                override fun getContractInfo(contractId: ContractId): Optional<ContractInfo> =
                    contractInfoCacheManager.getCache().get(contractId)?.let { contractInfo ->
                        Optional.of(contractInfo)
                    } ?: contractService.getContractInfo(contractId)
            }
        }
}
