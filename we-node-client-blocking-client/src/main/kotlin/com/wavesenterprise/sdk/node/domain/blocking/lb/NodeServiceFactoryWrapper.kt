package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PolicyId
import java.time.OffsetDateTime

interface NodeServiceFactoryWrapper {
    val name: String
    val nodeOwnerAddress: Address
    val keyStorePassword: Password
    val sequentialErrorCount: Long
    val quarantineUntil: OffsetDateTime

    fun status(): WeNodeApiWrapperStatus

    fun getAddresses(): List<Address>

    fun getNodeOwner(): NodeOwner

    fun getPolicyRecipients(policyId: PolicyId): List<Address>

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
