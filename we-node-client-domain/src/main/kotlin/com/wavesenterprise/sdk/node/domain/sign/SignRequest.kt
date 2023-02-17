package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.tx.Tx

sealed interface SignRequest<T : Tx> {
    val senderAddress: Address
    val password: Password?

    fun withAddress(address: Address): SignRequest<T>
    fun withPassword(password: Password?): SignRequest<T>
}
