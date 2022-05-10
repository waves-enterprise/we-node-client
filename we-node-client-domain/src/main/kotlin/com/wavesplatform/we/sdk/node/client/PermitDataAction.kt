package com.wavesplatform.we.sdk.node.client

sealed interface PermitDataAction {
    data class SetDataAction(
        val value: DataValue,
    ) : PermitDataAction

    object DeleteDataAction : PermitDataAction
}
