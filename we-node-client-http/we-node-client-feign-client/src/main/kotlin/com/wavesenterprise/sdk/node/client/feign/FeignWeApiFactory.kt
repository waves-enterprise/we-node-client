package com.wavesenterprise.sdk.node.client.feign

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import feign.Feign
import feign.Request
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.optionals.OptionalDecoder
import java.util.concurrent.TimeUnit

object FeignWeApiFactory {

    private val objectMapper = jacksonObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    fun <T> createClient(
        clientClass: Class<T>,
        feignProperties: FeignProperties,
    ): T = Feign.builder()
        .encoder(JacksonEncoder(objectMapper))
        .decoder(OptionalDecoder(JacksonDecoder(objectMapper)))
        .logLevel(feignProperties.loggerLevel)
        .dismiss404()
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
