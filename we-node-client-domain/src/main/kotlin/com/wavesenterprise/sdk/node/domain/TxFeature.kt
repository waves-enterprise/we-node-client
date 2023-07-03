package com.wavesenterprise.sdk.node.domain

enum class TxFeature {
    SPONSORED_FEES,
    SMART_ACCOUNTS,
    SMART_ASSETS,
    DATA_TRANSACTION,
    GRPC_CONTRACTS,
    MASS_TRANSFER,
    ATOMIC;

    val bitmask by lazy { 1 shl ordinal }
}

class DictionaryTxVersion private constructor(
    val type: TxType,
    val version: Int,
    private val features: Int
) {

    constructor(
        type: TxType,
        version: Int,
        features: Set<TxFeature> = setOf()
    ) : this(
        type = type,
        version = version,
        features = features.fold(0) { acc, feature -> acc or feature.bitmask }
    )

    fun supports(features: Set<TxFeature>) =
        this.features == features.fold(this.features) { acc, feature -> acc or feature.bitmask }
}
