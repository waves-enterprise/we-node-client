package com.wavesenterprise.sdk.node.exception.specific

import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.NodeErrorCode
import com.wavesenterprise.sdk.node.exception.NodeErrorCode.CONTRACT_NOT_FOUND
import com.wavesenterprise.sdk.node.exception.NodeErrorCode.CUSTOM_VALIDATION_ERROR
import com.wavesenterprise.sdk.node.exception.NodeErrorCode.DATA_KEY_NOT_EXISTS
import com.wavesenterprise.sdk.node.exception.NodeErrorCode.INVALID_ADDRESS
import com.wavesenterprise.sdk.node.exception.NodeErrorCode.INVALID_SIGNATURE
import com.wavesenterprise.sdk.node.exception.NodeErrorCode.POLICY_DOES_NOT_EXIST
import com.wavesenterprise.sdk.node.exception.NodeErrorCode.POLICY_ITEM_DATA_IS_MISSING
import com.wavesenterprise.sdk.node.exception.NodeErrorCode.PRIVACY_API_KEY_NOT_VALID
import com.wavesenterprise.sdk.node.exception.NodeException

object SpecificExceptionMapper {
    fun mapSpecificException(
        nodeError: NodeError,
        exception: Exception,
    ): NodeException? =
        NodeErrorCode.of(nodeError.error)?.let { code ->
            when (code) {
                INVALID_SIGNATURE -> InvalidSignatureException(nodeError, cause = exception)
                INVALID_ADDRESS -> InvalidAddressException(nodeError, cause = exception)
                CUSTOM_VALIDATION_ERROR -> CustomValidationErrorException(nodeError, cause = exception)
                DATA_KEY_NOT_EXISTS -> DataKeyNotExistsException(nodeError, cause = exception)
                CONTRACT_NOT_FOUND -> ContractNotFoundException(nodeError, cause = exception)
                POLICY_DOES_NOT_EXIST -> PolicyDoesNotExistException(nodeError, cause = exception)
                PRIVACY_API_KEY_NOT_VALID -> PrivacyApiKeyNotValidException(nodeError, cause = exception)
                POLICY_ITEM_DATA_IS_MISSING -> PolicyItemDataIsMissingException(nodeError, cause = exception)
            }
        }
}
