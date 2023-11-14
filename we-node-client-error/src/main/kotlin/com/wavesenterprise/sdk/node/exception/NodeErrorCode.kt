package com.wavesenterprise.sdk.node.exception

@Suppress("MagicNumber")
enum class NodeErrorCode(val code: Int) {
    INVALID_SIGNATURE(101),
    INVALID_ADDRESS(102),
    INVALID_PUBLIC_KEY(108),
    INVALID_PASSWORD(115),
    CUSTOM_VALIDATION_ERROR(199),
    DATA_KEY_NOT_EXISTS(304),
    CONTRACT_NOT_FOUND(600),
    POLICY_DOES_NOT_EXIST(612),
    PRIVACY_API_KEY_NOT_VALID(614),
    POLICY_ITEM_DATA_IS_MISSING(617),
    ;

    companion object {
        @JvmStatic
        fun of(code: Int) = values().singleOrNull { it.code == code }
    }
}
