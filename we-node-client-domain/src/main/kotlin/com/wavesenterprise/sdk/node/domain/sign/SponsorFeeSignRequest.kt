package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Enabled
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.SponsorFeeTx

data class SponsorFeeSignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val enabled: Enabled,
    val assetId: AssetId? = null,
) : SignRequest<SponsorFeeTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?) = copy(password = password)

    companion object {
        @JvmStatic
        fun SponsorFeeSignRequest.toTx(senderPublicKey: PublicKey) = SponsorFeeTx(
            id = TxId.EMPTY,
            senderPublicKey = senderPublicKey,
            assetId = assetId,
            enabled = enabled,
            fee = fee,
            timestamp = Timestamp(System.currentTimeMillis()),
            proofs = listOf(),
            senderAddress = senderAddress,
            version = requireNotNull(version),
        )
    }
}
