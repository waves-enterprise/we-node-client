package com.wavesplatform.we.sdk.node.client.sign

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.atomic.AtomicBadge
import com.wavesplatform.we.sdk.node.client.contract.ContractId
import com.wavesplatform.we.sdk.node.client.tx.DisableContractTx

data class DisableContractSignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val contractId: ContractId,
    val feeAssetId: FeeAssetId? = null,
    val atomicBadge: AtomicBadge? = null,
) : SignRequest<DisableContractTx>
