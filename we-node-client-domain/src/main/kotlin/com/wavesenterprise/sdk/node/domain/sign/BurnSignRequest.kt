package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Attachment
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.BurnTx

data class BurnSignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val assetId: AssetId? = null,
    val quantity: Quantity,
    val attachment: Attachment? = null,
) : SignRequest<BurnTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?) = copy(password = password)

    companion object {
        @JvmStatic
        fun BurnSignRequest.toTx(senderPublicKey: PublicKey, chainId: ChainId) = BurnTx(
            id = TxId.EMPTY,
            chainId = chainId,
            senderPublicKey = senderPublicKey,
            assetId = assetId,
            amount = Amount(quantity.value),
            fee = fee,
            timestamp = Timestamp(System.currentTimeMillis()),
            senderAddress = senderAddress,
            proofs = listOf(),
            version = requireNotNull(version),
        )
    }
}
