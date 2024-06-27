package com.wavesenterprise.sdk.node.domain

object TxVersionDictionary {

    fun maxVersionSupports(type: TxType, vararg features: TxFeature): Int =
        findFirstSorted(type, features) { -it.version }.version

    fun minVersionSupports(type: TxType, vararg features: TxFeature): Int =
        findFirstSorted(type, features) { -it.version }.version

    private fun <T : Comparable<T>> findFirstSorted(
        type: TxType,
        features: Array<out TxFeature>,
        selector: (DictionaryTxVersion) -> T,
    ) = checkNotNull(
        allVersionsGroupedByTxType[type]?.sortedBy(selector)?.firstOrNull { it.supports(features = features.toSet()) },
    ) {
        "No transaction $type version supports $features"
    }

    private fun describeDictionaryTxVersions() = transactions(
        TxType.REGISTER_NODE has versions(
            1 with noFeatures,
        ),
        TxType.CREATE_ALIAS has versions(
            2 with noFeatures,
            3 with features(TxFeature.SPONSORED_FEES),
        ),
        TxType.ISSUE has versions(
            2 with features(TxFeature.SMART_ACCOUNTS, TxFeature.SMART_ASSETS),
        ),
        TxType.REISSUE has versions(
            2 with features(TxFeature.SMART_ACCOUNTS),
        ),
        TxType.BURN has versions(
            2 with features(TxFeature.SMART_ACCOUNTS),
        ),
        TxType.LEASE has versions(
            2 with features(TxFeature.SMART_ACCOUNTS),
        ),
        TxType.LEASE_CANCEL has versions(
            2 with features(TxFeature.SMART_ACCOUNTS),
        ),
        TxType.SPONSOR_FEE has versions(
            1 with features(TxFeature.SPONSORED_FEES),
        ),
        TxType.SET_ASSET_SCRIPT has versions(
            1 with features(TxFeature.SMART_ASSETS),
        ),
        TxType.DATA has versions(
            1 with features(TxFeature.DATA_TRANSACTION),
            2 with features(TxFeature.SPONSORED_FEES, TxFeature.DATA_TRANSACTION),
        ),
        TxType.TRANSFER has versions(
            2 with features(TxFeature.SPONSORED_FEES, TxFeature.SMART_ACCOUNTS),
            3 with features(TxFeature.SPONSORED_FEES, TxFeature.SMART_ACCOUNTS, TxFeature.ATOMIC),
        ),
        TxType.MASS_TRANSFER has versions(
            1 with features(TxFeature.MASS_TRANSFER),
            2 with features(TxFeature.SPONSORED_FEES, TxFeature.MASS_TRANSFER),
        ),
        TxType.PERMIT has versions(
            1 with noFeatures,
            2 with features(TxFeature.ATOMIC),
        ),
        TxType.CREATE_POLICY has versions(
            1 with noFeatures,
            2 with features(TxFeature.SPONSORED_FEES),
            3 with features(TxFeature.SPONSORED_FEES, TxFeature.ATOMIC),
        ),
        TxType.UPDATE_POLICY has versions(
            1 with noFeatures,
            2 with features(TxFeature.SPONSORED_FEES),
            3 with features(TxFeature.SPONSORED_FEES, TxFeature.ATOMIC),
        ),
        TxType.POLICY_DATA_HASH has versions(
            1 with noFeatures,
            2 with features(TxFeature.SPONSORED_FEES),
            3 with features(TxFeature.SPONSORED_FEES, TxFeature.ATOMIC),
        ),
        TxType.CREATE_CONTRACT has versions(
            1 with noFeatures,
            2 with features(TxFeature.SPONSORED_FEES, TxFeature.GRPC_CONTRACTS),
            3 with features(TxFeature.SPONSORED_FEES, TxFeature.GRPC_CONTRACTS, TxFeature.ATOMIC),
        ),
        TxType.CALL_CONTRACT has versions(
            1 with noFeatures,
            2 with noFeatures,
            3 with features(TxFeature.SPONSORED_FEES),
            4 with features(TxFeature.SPONSORED_FEES, TxFeature.ATOMIC),
        ),
        TxType.EXECUTED_CONTRACT has versions(
            1 with noFeatures,
            2 with noFeatures,
        ),
        TxType.DISABLE_CONTRACT has versions(
            1 with noFeatures,
            2 with features(TxFeature.SPONSORED_FEES),
            3 with features(TxFeature.SPONSORED_FEES, TxFeature.ATOMIC),
        ),
        TxType.UPDATE_CONTRACT has versions(
            1 with noFeatures,
            2 with features(TxFeature.SPONSORED_FEES),
            3 with features(TxFeature.SPONSORED_FEES, TxFeature.ATOMIC),
        ),
        TxType.SET_SCRIPT has versions(
            1 with features(TxFeature.SMART_ACCOUNTS),
        ),
        TxType.ATOMIC has versions(
            1 with noFeatures,
        ),
    )

    private val noFeatures = emptySet<TxFeature>()

    private val allVersionsGroupedByTxType = describeDictionaryTxVersions().groupBy { it.type }

    private fun transactions(vararg versions: List<DictionaryTxVersion>) =
        versions.flatMap { it }

    private infix fun TxType.has(versions: Map<Int, Set<TxFeature>>): List<DictionaryTxVersion> =
        versions.map { DictionaryTxVersion(this, it.key, it.value) }

    private fun versions(vararg versions: Pair<Int, Set<TxFeature>>): Map<Int, Set<TxFeature>> =
        versions.toMap()

    private infix fun Int.with(features: Set<TxFeature>) =
        this to features

    private fun features(vararg features: TxFeature) =
        features.toSet()
}
