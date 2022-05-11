package com.wavesplatform.we.sdk.node.client.http.privacy

import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemDataResponse
import java.util.Base64

private val BASE_64_DECODER = Base64.getDecoder()

fun String.fromBase64toPolicyItemDataResponse(): PolicyItemDataResponse =
    PolicyItemDataResponse(
        data = BASE_64_DECODER.decode(this)
    )
