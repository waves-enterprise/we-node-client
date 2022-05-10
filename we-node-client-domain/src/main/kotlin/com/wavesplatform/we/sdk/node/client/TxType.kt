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
        get() = TxType.values().firstOrNull { txType ->
            txType.code == this
        } ?: error("TxType not found for code $this")

    companion object {
        const val CREATE_CONTRACT_TX_TYPE_STRING = "103"
    }
}
