package com.wavesenterprise.sdk.node.client.feign

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import feign.Feign
import feign.Request
import feign.codec.ErrorDecoder
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.optionals.OptionalDecoder
import feign.slf4j.Slf4jLogger
import java.util.concurrent.TimeUnit

object FeignWeApiFactory {

    private val objectMapper = jacksonObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    fun <T> createClient(
        clientClass: Class<T>,
        loggerName: String = clientClass.simpleName,
        feignProperties: FeignProperties,
        errorDecoder: ErrorDecoder? = null,
    ): T = Feign.builder()
        .encoder(JacksonEncoder(objectMapper))
        .decoder(OptionalDecoder(JacksonDecoder(objectMapper)))
        .errorDecoder(errorDecoder ?: ErrorDecoder.Default())
        .logLevel(feignProperties.loggerLevel)
        .logger(Slf4jLogger(loggerName))
        .options(
            Request.Options(
                feignProperties.connectTimeout,
                TimeUnit.MILLISECONDS,
                feignProperties.readTimeout,
                TimeUnit.MILLISECONDS,
                true,
            )
        )
        .target(clientClass, feignProperties.url)
}
