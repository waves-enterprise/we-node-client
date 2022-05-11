package com.wavesplatform.we.sdk.node.client.sign

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.ContractApiVersion
import com.wavesplatform.we.sdk.node.client.ContractImage
import com.wavesplatform.we.sdk.node.client.ContractName
import com.wavesplatform.we.sdk.node.client.DataEntry
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.ValidationPolicy
import com.wavesplatform.we.sdk.node.client.atomic.AtomicBadge
import com.wavesplatform.we.sdk.node.client.tx.CreateContractTx

data class CreateContractSignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val image: ContractImage,
    val imageHash: Hash,
    val contractName: ContractName,
    val params: List<DataEntry>,
    val apiVersion: ContractApiVersion? = null,
    val validationPolicy: ValidationPolicy? = null,
    val atomicBadge: AtomicBadge? = null,
) : SignRequest<CreateContractTx>
