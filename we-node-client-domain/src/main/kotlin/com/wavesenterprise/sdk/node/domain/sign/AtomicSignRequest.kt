package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.AtomicSignInnerTx
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx

data class AtomicSignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val txs: List<AtomicSignInnerTx<*>>,
) : SignRequest<AtomicTx>
