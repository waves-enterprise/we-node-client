package com.wavesenterprise.sdk.node.client.feign

import feign.Logger
import feign.codec.ErrorDecoder

data class FeignNodeClientParams(
    override val url: String,
    override val decode404: Boolean = true,
    override val connectTimeout: Long = 5000,
    override val readTimeout: Long = 3000,
    override val loggerLevel: Logger.Level = Logger.Level.FULL,
    override val errorDecoder: ErrorDecoder? = null,
) : FeignProperties
