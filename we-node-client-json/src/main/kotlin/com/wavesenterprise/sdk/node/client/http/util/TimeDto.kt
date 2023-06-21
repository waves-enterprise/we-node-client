package com.wavesenterprise.sdk.node.client.http.util

import com.fasterxml.jackson.annotation.JsonProperty
import com.wavesenterprise.sdk.node.domain.util.Time

data class TimeDto(
    val system: Long,
    @JsonProperty("NTP")
    val ntp: Long,
) {

    companion object {

        @JvmStatic
        fun Time.toDto() = TimeDto(
            system = system,
            ntp = ntp,
        )

        @JvmStatic
        fun TimeDto.toDomain() = Time(
            system = system,
            ntp = ntp,
        )
    }
}
