package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Decimals
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.IssueTxDescription
import com.wavesenterprise.sdk.node.domain.IssueTxName
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Reissuable
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.IssueTx

data class IssueSignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val name: IssueTxName,
    val quantity: Quantity,
    val description: IssueTxDescription,
    val decimals: Decimals,
    val reissuable: Reissuable,
) : SignRequest<IssueTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?) = copy(password = password)

    companion object {
        @JvmStatic
        fun IssueSignRequest.toTx(senderPublicKey: PublicKey, chainId: ChainId) = IssueTx(
            id = TxId.EMPTY,
            chainId = chainId,
            senderPublicKey = senderPublicKey,
            name = name,
            description = description,
            quantity = quantity,
            decimals = decimals,
            reissuable = reissuable,
            fee = fee,
            timestamp = Timestamp(System.currentTimeMillis()),
            script = null,
            proofs = listOf(),
            senderAddress = senderAddress,
            version = requireNotNull(version),
        )
    }
}
