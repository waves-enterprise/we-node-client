package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.Script
import com.wavesenterprise.sdk.node.domain.ScriptName
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.SetScriptTx

data class SetScriptSignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val name: ScriptName,
    val script: Script? = null,
) : SignRequest<SetScriptTx>
