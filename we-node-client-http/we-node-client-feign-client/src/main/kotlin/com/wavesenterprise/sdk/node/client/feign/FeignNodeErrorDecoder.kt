package com.wavesenterprise.sdk.node.client.feign

import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder

class FeignNodeErrorDecoder(
    private val feignNodeErrorMapper: FeignNodeErrorMapper,
) : ErrorDecoder {

    private val defaultDecoder = ErrorDecoder.Default()

    override fun decode(methodKey: String, response: Response): Exception {
        val exception = when (val defaultDecodedException = defaultDecoder.decode(methodKey, response)) {
            is FeignException -> return feignNodeErrorMapper.mapToGeneralException(defaultDecodedException)
            else -> defaultDecodedException
        }
        return exception
    }
}
