package com.wavesenterprise.sdk.node.client.feign.tx

import com.wavesenterprise.sdk.node.client.http.sign.SignRequestDto
import com.wavesenterprise.sdk.node.client.http.tx.TxDto
import com.wavesenterprise.sdk.node.client.http.tx.UtxSizeDto
import feign.Headers
import feign.Param
import feign.RequestLine
import java.util.Optional

interface WeTxApiFeign {

    @Headers("Content-Type: application/json")
    @RequestLine("POST /transactions/sign")
    fun <T : TxDto> sign(request: SignRequestDto<T>): T

    @Headers("Content-Type: application/json")
    @RequestLine("POST /transactions/signAndBroadcast")
    fun <T : TxDto> signAndBroadcast(request: SignRequestDto<T>): T

    @Headers("Content-Type: application/json")
    @RequestLine("POST /transactions/broadcast")
    fun <T : TxDto> broadcast(request: T): T

    @RequestLine("GET /transactions/info/{id}")
    fun txInfo(@Param("id") id: String): Optional<TxDto>

    @RequestLine("GET /transactions/unconfirmed/size")
    fun utxInfo(): UtxSizeDto
}
