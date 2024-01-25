package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.NodeName
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.RegisterNodeTx

data class RegisterNodeSignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val opType: OpType,
    val target: Address,
    val targetPublicKey: PublicKey,
    val nodeName: NodeName,
) : SignRequest<RegisterNodeTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?) = copy(password = password)

    companion object {
        @JvmStatic
        fun RegisterNodeSignRequest.toTx(senderPublicKey: PublicKey) = RegisterNodeTx(
            id = TxId.EMPTY,
            senderPublicKey = senderPublicKey,
            target = target,
            targetPublicKey = targetPublicKey,
            nodeName = nodeName,
            opType = opType,
            timestamp = Timestamp(System.currentTimeMillis()),
            fee = fee,
            proofs = listOf(),
            senderAddress = senderAddress,
            version = requireNotNull(version),
        )
    }
}
