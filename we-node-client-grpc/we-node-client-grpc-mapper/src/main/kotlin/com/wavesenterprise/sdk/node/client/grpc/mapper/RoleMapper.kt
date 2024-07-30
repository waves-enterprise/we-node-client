@file:Suppress("MagicNumber")

package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.wavesenterprise.sdk.node.domain.Role
import com.wavesenterprise.transaction.protobuf.role
import com.wavesenterprise.transaction.protobuf.Role as ProtoRole

object RoleMapper {
    @JvmStatic
    fun Role.dto(): ProtoRole =
        when (this) {
            Role.MINER -> 1
            Role.ISSUER -> 2
            Role.DEX -> 3
            Role.PERMISSIONER -> 4
            Role.BLACKLISTER -> 5
            Role.BANNED -> 6
            Role.CONTRACT_DEVELOPER -> 7
            Role.CONNECTION_MANAGER -> 8
            Role.SENDER -> 9
            Role.CONTRACT_VALIDATOR -> 10
        }.let { roleId ->
            role {
                id = roleId
            }
        }

    @JvmStatic
    fun ProtoRole.domain(): Role =
        when (id) {
            1 -> Role.MINER
            2 -> Role.ISSUER
            3 -> Role.DEX
            4 -> Role.PERMISSIONER
            5 -> Role.BLACKLISTER
            6 -> Role.BANNED
            7 -> Role.CONTRACT_DEVELOPER
            8 -> Role.CONNECTION_MANAGER
            9 -> Role.SENDER
            10 -> Role.CONTRACT_VALIDATOR
            else -> error("Unknown role id $this")
        }
}
