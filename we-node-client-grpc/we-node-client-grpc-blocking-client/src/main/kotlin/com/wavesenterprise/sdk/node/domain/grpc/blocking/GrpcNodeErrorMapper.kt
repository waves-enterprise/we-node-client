package com.wavesenterprise.sdk.node.domain.grpc.blocking

import com.sun.org.slf4j.internal.LoggerFactory
import com.wavesenterprise.sdk.node.exception.NodeBadRequestException
import com.wavesenterprise.sdk.node.exception.NodeConflictException
import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.NodeException
import com.wavesenterprise.sdk.node.exception.NodeForbiddenException
import com.wavesenterprise.sdk.node.exception.NodeInternalServerErrorException
import com.wavesenterprise.sdk.node.exception.NodeNotFoundException
import com.wavesenterprise.sdk.node.exception.NodeNotImplementedException
import com.wavesenterprise.sdk.node.exception.NodeServiceUnavailableException
import com.wavesenterprise.sdk.node.exception.NodeTooManyRequestsException
import com.wavesenterprise.sdk.node.exception.NodeUnauthorizedException
import com.wavesenterprise.sdk.node.exception.NodeUnexpectedException
import com.wavesenterprise.sdk.node.exception.specific.SpecificExceptionMapper
import io.grpc.Metadata
import io.grpc.Status.ABORTED
import io.grpc.Status.ALREADY_EXISTS
import io.grpc.Status.CANCELLED
import io.grpc.Status.DATA_LOSS
import io.grpc.Status.DEADLINE_EXCEEDED
import io.grpc.Status.FAILED_PRECONDITION
import io.grpc.Status.INTERNAL
import io.grpc.Status.INVALID_ARGUMENT
import io.grpc.Status.NOT_FOUND
import io.grpc.Status.OUT_OF_RANGE
import io.grpc.Status.PERMISSION_DENIED
import io.grpc.Status.RESOURCE_EXHAUSTED
import io.grpc.Status.UNAUTHENTICATED
import io.grpc.Status.UNAVAILABLE
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.Status.UNKNOWN
import io.grpc.StatusRuntimeException

/**
 * status docs: https://cloud.yandex.com/en/docs/api-design-guide/concepts/errors
 */
// TODO: DOC
object GrpcNodeErrorMapper {

    private const val ERROR_CODE_KEY = "error-code"
    private const val ERROR_MESSAGE_KEY = "error-message"

    private val LOG = LoggerFactory.getLogger(GrpcNodeErrorMapper::class.java)

    fun mapToGeneralException(ex: Exception): Exception {
        ex as StatusRuntimeException
        val nodeError: NodeError? = tryParseError(ex.trailers)
        val exception: NodeException = nodeError?.run {
            SpecificExceptionMapper.mapSpecificException(this, ex)
                ?: decodeUnexpectedExceptionWithNodeError(this, ex)
        } ?: decodeCommonException(ex) ?: NodeException(ex)
        return exception
    }

    private fun decodeCommonException(ex: StatusRuntimeException): NodeException? {
        return when (ex.status) {
            CANCELLED -> NodeUnexpectedException(cause = ex)
            UNKNOWN -> NodeInternalServerErrorException(cause = ex)
            INVALID_ARGUMENT -> NodeBadRequestException(cause = ex)
            DEADLINE_EXCEEDED -> NodeInternalServerErrorException(cause = ex)
            NOT_FOUND -> NodeNotFoundException(cause = ex)
            ALREADY_EXISTS -> NodeConflictException(cause = ex)
            PERMISSION_DENIED -> NodeForbiddenException(cause = ex)
            RESOURCE_EXHAUSTED -> NodeTooManyRequestsException(cause = ex)
            FAILED_PRECONDITION -> NodeBadRequestException(cause = ex)
            ABORTED -> NodeConflictException(cause = ex)
            OUT_OF_RANGE -> NodeBadRequestException(cause = ex)
            UNIMPLEMENTED -> NodeNotImplementedException(cause = ex)
            INTERNAL -> NodeInternalServerErrorException(cause = ex)
            UNAVAILABLE -> NodeServiceUnavailableException(cause = ex)
            DATA_LOSS -> NodeInternalServerErrorException(cause = ex)
            UNAUTHENTICATED -> NodeUnauthorizedException(cause = ex)
            else -> null
        }
    }

    private fun decodeUnexpectedExceptionWithNodeError(
        nodeError: NodeError,
        ex: StatusRuntimeException,
    ): NodeException? =
        when (ex.status) {
            INVALID_ARGUMENT -> NodeBadRequestException(nodeError = nodeError, cause = ex)
            NOT_FOUND -> NodeNotFoundException(nodeError = nodeError, cause = ex)
            FAILED_PRECONDITION -> NodeBadRequestException(nodeError = nodeError, cause = ex)
            OUT_OF_RANGE -> NodeBadRequestException(nodeError = nodeError, cause = ex)
            ALREADY_EXISTS -> NodeConflictException(nodeError = nodeError, cause = ex)
            else -> null
        }

    private fun tryParseError(trailers: Metadata?): NodeError? {
        return try {
            if (trailers == null) return null
            val errorCode = trailers.get(Metadata.Key.of(ERROR_CODE_KEY, Metadata.ASCII_STRING_MARSHALLER))
            val errorMessage = trailers.get(Metadata.Key.of(ERROR_MESSAGE_KEY, Metadata.ASCII_STRING_MARSHALLER))
            if (errorCode != null && errorMessage != null) {
                NodeError(
                    error = errorCode.toInt(),
                    message = errorMessage,
                )
            } else {
                null
            }
        } catch (ex: Exception) {
            LOG.warn("Unable to parse node error, response trailers: $trailers", ex)
            null
        }
    }
}
