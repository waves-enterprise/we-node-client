package com.wavesenterprise.sdk.node.client.http

import com.wavesenterprise.sdk.node.domain.Role

object RoleConstants {
    const val PERMISSIONER = "permissioner"
    const val SENDER = "sender"
    const val BLACKLISTER = "blacklister"
    const val MINER = "miner"
    const val ISSUER = "issuer"
    const val CONTRACT_DEVELOPER = "contract_developer"
    const val CONNECTION_MANAGER = "connection_manager"
    const val CONTRACT_VALIDATOR = "contract_validator"
    const val BANNED = "banned"
    const val DEX = "dex"

    @JvmStatic
    fun Role.toDto(): String =
        when (this) {
            Role.PERMISSIONER -> PERMISSIONER
            Role.SENDER -> SENDER
            Role.BLACKLISTER -> BLACKLISTER
            Role.MINER -> MINER
            Role.ISSUER -> ISSUER
            Role.CONTRACT_DEVELOPER -> CONTRACT_DEVELOPER
            Role.CONNECTION_MANAGER -> CONNECTION_MANAGER
            Role.CONTRACT_VALIDATOR -> CONTRACT_VALIDATOR
            Role.BANNED -> BANNED
            Role.DEX -> DEX
        }

    @JvmStatic
    fun String.fromRoleDtoToDomain(): Role =
        when (this) {
            PERMISSIONER -> Role.PERMISSIONER
            SENDER -> Role.SENDER
            BLACKLISTER -> Role.BLACKLISTER
            MINER -> Role.MINER
            ISSUER -> Role.ISSUER
            CONTRACT_DEVELOPER -> Role.CONTRACT_DEVELOPER
            CONNECTION_MANAGER -> Role.CONNECTION_MANAGER
            CONTRACT_VALIDATOR -> Role.CONTRACT_VALIDATOR
            BANNED -> Role.BANNED
            else -> error("Unknown role $this")
        }
}
