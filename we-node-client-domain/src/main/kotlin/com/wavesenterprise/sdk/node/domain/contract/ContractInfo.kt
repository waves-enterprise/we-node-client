package com.wavesenterprise.sdk.node.domain.contract

data class ContractInfo(
    val id: ContractId,
    val image: ContractImage,
    val imageHash: ContractImageHash,
    val version: ContractVersion,
    val active: Boolean,
)
