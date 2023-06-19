package com.wavesenterprise.sdk.node.client.feign

import feign.Response
import feign.codec.Decoder
import java.lang.reflect.Type

open class JacksonByteArrayDecoder(
    private val decoder: Decoder,
) : Decoder by decoder {

    override fun decode(response: Response?, type: Type?): Any? {
        if (type == ByteArray::class.java && response != null)
            return response.body().asInputStream().use { it.readBytes() }
        return decoder.decode(response, type)
    }
}
