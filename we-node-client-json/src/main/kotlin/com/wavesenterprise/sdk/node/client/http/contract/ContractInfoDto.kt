package com.wavesenterprise.sdk.node.client.http.contract

import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.contract.ContractInfo
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion

data class ContractInfoDto(
    val contractId: String,
    val image: String,
    val imageHash: String,
    val version: Int,
    val active: Boolean,
) {

    companion object {

        @JvmStatic
        fun ContractInfoDto.toDomain() =
            ContractInfo(
                id = ContractId.fromBase58(contractId),
                image = ContractImage.fromString(image),
                imageHash = ContractImageHash.fromString(imageHash),
                version = ContractVersion.fromInt(version),
                active = active,
            )
    }
}
