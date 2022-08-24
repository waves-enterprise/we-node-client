package com.wavesenterprise.sdk.node.client.feign

import feign.Logger
import feign.codec.ErrorDecoder

interface FeignProperties {
    val url: String
    val decode404: Boolean
    val connectTimeout: Long
    val readTimeout: Long
    val loggerLevel: Logger.Level
    val errorDecoder: ErrorDecoder?
}
