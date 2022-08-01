package com.wavesenterprise.sdk.node.client.feign

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import feign.Feign
import feign.Logger
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.optionals.OptionalDecoder

object FeignWeApiFactory {

    private val objectMapper = jacksonObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    fun <T> createClient(
        clientClass: Class<T>,
        feignProperties: FeignProperties
    ): T = Feign.builder()
        .encoder(JacksonEncoder(objectMapper))
        .logLevel(Logger.Level.FULL)
        .decoder(OptionalDecoder(JacksonDecoder(objectMapper)))
        .dismiss404()
        .target(clientClass, feignProperties.url)
}
