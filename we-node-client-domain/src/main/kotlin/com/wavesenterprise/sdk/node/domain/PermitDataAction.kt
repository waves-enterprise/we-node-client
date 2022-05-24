package com.wavesenterprise.sdk.node.domain

sealed interface PermitDataAction {
    data class SetDataAction(
        val value: DataValue,
    ) : PermitDataAction

    object DeleteDataAction : PermitDataAction
}
