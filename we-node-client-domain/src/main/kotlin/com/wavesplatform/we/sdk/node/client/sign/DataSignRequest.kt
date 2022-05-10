package com.wavesplatform.we.sdk.node.client.sign

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.PermitDataEntry
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.DataTx

data class DataSignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val author: Address,
    val data: List<PermitDataEntry>,
) : SignRequest<DataTx>
