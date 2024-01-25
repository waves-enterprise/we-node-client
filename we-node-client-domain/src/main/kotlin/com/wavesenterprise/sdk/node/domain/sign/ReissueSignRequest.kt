package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Reissuable
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.ReissueTx

data class ReissueSignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val quantity: Quantity,
    val assetId: AssetId? = null,
    val reissuable: Reissuable,
) : SignRequest<ReissueTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?) = copy(password = password)

    companion object {
        @JvmStatic
        fun ReissueSignRequest.toTx(senderPublicKey: PublicKey, chainId: ChainId) = ReissueTx(
            id = TxId.EMPTY,
            chainId = chainId,
            senderPublicKey = senderPublicKey,
            assetId = assetId,
            quantity = quantity,
            reissuable = reissuable,
            fee = fee,
            timestamp = Timestamp(System.currentTimeMillis()),
            proofs = listOf(),
            senderAddress = senderAddress,
            version = requireNotNull(version),
        )
    }
}
