package com.wavesenterprise.sdk.node.client.feign

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
import com.wavesenterprise.sdk.node.exception.NodeUnprocessableEntityException
import com.wavesenterprise.sdk.node.exception.specific.SpecificExceptionMapper
import feign.FeignException
import feign.FeignException.BadRequest
import feign.FeignException.Conflict
import feign.FeignException.Forbidden
import feign.FeignException.InternalServerError
import feign.FeignException.NotFound
import feign.FeignException.NotImplemented
import feign.FeignException.ServiceUnavailable
import feign.FeignException.TooManyRequests
import feign.FeignException.Unauthorized
import feign.FeignException.UnprocessableEntity

class FeignNodeErrorMapper(
    private val objectMapper: ObjectMapper,
) {

    private val LOG = LoggerFactory.getLogger(FeignNodeErrorMapper::class.java)

    fun mapToGeneralException(ex: Exception): NodeException {
        val nodeError: NodeError? = tryParseError((ex as FeignException).contentUTF8())
        val exception: NodeException = nodeError?.run {
            SpecificExceptionMapper.mapSpecificException(this, ex) ?: decodeUnexpectedExceptionWithNodeError(this, ex)
        } ?: decodeCommonException(ex) ?: NodeException(ex)
        return exception
    }

    private fun decodeCommonException(feignException: FeignException): NodeException? =
        when (feignException) {
            is BadRequest -> NodeBadRequestException(cause = feignException)
            is NotFound -> NodeNotFoundException(cause = feignException)
            is Unauthorized -> NodeUnauthorizedException(cause = feignException)
            is Forbidden -> NodeForbiddenException(cause = feignException)
            is InternalServerError -> NodeInternalServerErrorException(cause = feignException)
            is NotImplemented -> NodeNotImplementedException(cause = feignException)
            is ServiceUnavailable -> NodeServiceUnavailableException(cause = feignException)
            is Conflict -> NodeConflictException(cause = feignException)
            is TooManyRequests -> NodeTooManyRequestsException(cause = feignException)
            is UnprocessableEntity -> NodeUnprocessableEntityException(cause = feignException)
            else -> null
        }

    private fun decodeUnexpectedExceptionWithNodeError(
        error: NodeError,
        feignException: FeignException
    ): NodeException? =
        when (feignException) {
            is BadRequest -> NodeBadRequestException(nodeError = error, cause = feignException)
            is NotFound -> NodeNotFoundException(nodeError = error, cause = feignException)
            is Conflict -> NodeConflictException(nodeError = error, cause = feignException)
            else -> null
        }

    private fun tryParseError(content: String) =
        try {
            objectMapper.readValue<NodeError>(content)
        } catch (e: Exception) {
            LOG.warn("Unable to parse node error, response content: $content", e)
            null
        }
}
