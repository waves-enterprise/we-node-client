package com.wavesenterprise.sdk.node.client.http.contract

import com.fasterxml.jackson.annotation.JsonProperty

enum class TxStatusDto {
    @JsonProperty("Failure")
    FAILURE,

    @JsonProperty("Success")
    SUCCESS,

    @JsonProperty("Error")
    ERROR,
    ;
}
