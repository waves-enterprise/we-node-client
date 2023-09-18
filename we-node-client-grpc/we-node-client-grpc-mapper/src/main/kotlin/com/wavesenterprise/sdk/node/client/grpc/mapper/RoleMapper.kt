package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.wavesenterprise.sdk.node.domain.Role
import com.wavesenterprise.transaction.protobuf.role
import com.wavesenterprise.transaction.protobuf.Role as ProtoRole

object RoleMapper {
    @JvmStatic
    fun Role.dto(): ProtoRole =
        when (this) {
            Role.PERMISSIONER -> 0
            Role.SENDER -> 1
            Role.BLACKLISTER -> 2
            Role.MINER -> 3
            Role.ISSUER -> 4
            Role.CONTRACT_DEVELOPER -> 5
            Role.CONNECTION_MANAGER -> 6
            Role.CONTRACT_VALIDATOR -> 7
            Role.BANNED -> 8
        }.let { roleId ->
            role {
                id = roleId
            }
        }

    @JvmStatic
    fun ProtoRole.domain(): Role =
        when (id) {
            0 -> Role.PERMISSIONER
            1 -> Role.SENDER
            2 -> Role.BLACKLISTER
            3 -> Role.MINER
            4 -> Role.ISSUER
            5 -> Role.CONTRACT_DEVELOPER
            6 -> Role.CONNECTION_MANAGER
            7 -> Role.CONTRACT_VALIDATOR
            8 -> Role.BANNED
            else -> error("Unknown role id $this")
        }
}
