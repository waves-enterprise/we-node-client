package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory
import java.time.OffsetDateTime
import kotlin.math.min
import kotlin.math.pow

class DefaultNodeServiceFactoryWrapper(
    private val nodeBlockingServiceFactory: NodeBlockingServiceFactory,
    override val name: String,
    override var sequentialErrorCount: Long = 0,
    override var quarantineUntil: OffsetDateTime = OffsetDateTime.MIN,
) : NodeServiceFactoryWrapper, NodeBlockingServiceFactory by nodeBlockingServiceFactory {

    private val addresses: List<Address> = nodeBlockingServiceFactory.addressService().getAddresses()
    private val nodeOwner: NodeOwner = nodeBlockingServiceFactory.nodeInfoService().getNodeOwner()

    override fun status(): WeNodeApiWrapperStatus =
        if (sequentialErrorCount == 0L || OffsetDateTime.now().isAfter(quarantineUntil)) WeNodeApiWrapperStatus.ALIVE
        else WeNodeApiWrapperStatus.QUARANTINE

    override fun getAddresses(): List<Address> = addresses

    override fun getNodeOwnerAddress(): NodeOwner = nodeOwner

    override fun tryReturnIntoRotation(): Boolean =
        synchronized(this) {
            when (sequentialErrorCount) {
                0L -> false
                else -> {
                    sequentialErrorCount = 0
                    true
                }
            }
        }

    override fun invocationFailed(minQuarantineDelay: Long, maxQuarantineDelay: Long): Boolean =
        synchronized(this) {
            val now = OffsetDateTime.now()
            when {
                now.isAfter(quarantineUntil) -> {
                    sequentialErrorCount++
                    quarantineUntil = now.plusSeconds(
                        min(
                            maxQuarantineDelay.toFloat(),
                            minQuarantineDelay.toFloat().pow(sequentialErrorCount.toFloat()),
                        ).toLong()
                    )
                    true
                }
                else -> false
            }
        }
}
