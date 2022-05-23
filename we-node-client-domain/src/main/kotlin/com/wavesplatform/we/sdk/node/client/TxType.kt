package com.wavesplatform.we.sdk.node.client

enum class TxType(val code: Int) {
    GENESIS(1),
    ISSUE(3),
    TRANSFER(4),
    REISSUE(5),
    BURN(6),
    LEASE(8),
    LEASE_CANCEL(9),
    CREATE_ALIAS(10),
    MASS_TRANSFER(11),
    DATA(12),
    SET_SCRIPT(13),
    SPONSOR_FEE(14),
    SET_ASSET_SCRIPT(15),
    GENESIS_PERMIT(101),
    PERMIT(102),
    CREATE_CONTRACT(103),
    CALL_CONTRACT(104),
    EXECUTED_CONTRACT(105),
    DISABLE_CONTRACT(106),
    UPDATE_CONTRACT(107),
    GENESIS_REGISTER_NODE(110),
    REGISTER_NODE(111),
    CREATE_POLICY(112),
    UPDATE_POLICY(113),
    POLICY_DATA_HASH(114),
    ATOMIC(120),
    ;

    inline val Int.txType: TxType
        get() = fromInt(this)

    companion object {

        @JvmStatic
        fun fromInt(txType: Int): TxType = TxType.values().firstOrNull {
            it.code == txType
        } ?: error("TxType not found for code $this")

        const val GENESIS_TX_TYPE_STRING = "1"
        const val ISSUE_TX_TYPE_STRING = "3"
        const val TRANSFER_TX_TYPE_STRING = "4"
        const val REISSUE_TX_TYPE_STRING = "5"
        const val BURN_TX_TYPE_STRING = "6"
        const val LEASE_TX_TYPE_STRING = "8"
        const val LEASE_CANCEL_TX_TYPE_STRING = "9"
        const val CREATE_ALIAS_TX_TYPE_STRING = "10"
        const val MASS_TRANSFER_TX_TYPE_STRING = "11"
        const val DATA_TX_TYPE_STRING = "12"
        const val SET_SCRIPT_TX_TYPE_STRING = "13"
        const val SPONSOR_FEE_TX_TYPE_STRING = "14"
        const val SET_ASSET_SCRIPT_TX_TYPE_STRING = "15"
        const val GENESIS_PERMIT_TX_TYPE_STRING = "101"
        const val PERMIT_TX_TYPE_STRING = "102"
        const val CREATE_CONTRACT_TX_TYPE_STRING = "103"
        const val CALL_CONTRACT_TX_TYPE_STRING = "104"
        const val EXECUTED_CONTRACT_TX_TYPE_STRING = "105"
        const val DISABLE_CONTRACT_TX_TYPE_STRING = "106"
        const val UPDATE_CONTRACT_TX_TYPE_STRING = "107"
        const val GENESIS_REGISTER_NODE_TX_TYPE_STRING = "110"
        const val REGISTER_NODE_TX_TYPE_STRING = "111"
        const val CREATE_POLICY_TX_TYPE_STRING = "112"
        const val UPDATE_POLICY_TX_TYPE_STRING = "113"
        const val POLICY_DATA_HASH_TX_TYPE_STRING = "114"
        const val ATOMIC_TX_TYPE_STRING = "120"
    }
}
