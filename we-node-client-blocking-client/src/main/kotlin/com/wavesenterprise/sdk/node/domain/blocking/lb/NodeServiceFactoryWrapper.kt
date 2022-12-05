package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import java.time.OffsetDateTime

interface NodeServiceFactoryWrapper {
    val name: String
    val sequentialErrorCount: Long
    val quarantineUntil: OffsetDateTime

    fun status(): WeNodeApiWrapperStatus

    fun getAddresses(): List<Address>

    fun getNodeOwnerAddress(): NodeOwner

    fun tryReturnIntoRotation(): Boolean

    fun invocationFailed(minQuarantineDelay: Long, maxQuarantineDelay: Long): Boolean
}

enum class WeNodeApiWrapperStatus {
    ALIVE,
    QUARANTINE,
    ;
}

fun NodeServiceFactoryWrapper.isAlive(): Boolean =
    WeNodeApiWrapperStatus.ALIVE == status()
