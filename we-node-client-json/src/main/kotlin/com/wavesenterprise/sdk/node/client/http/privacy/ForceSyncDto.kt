package com.wavesenterprise.sdk.node.client.http.privacy

import com.wavesenterprise.sdk.node.domain.privacy.ForceSync

data class ForceSyncDto(
    val forceRestarted: Long,
) {
    companion object {
        @JvmStatic
        fun ForceSyncDto.toDomain() =
            ForceSync(
                forceRestarted = forceRestarted,
            )

        @JvmStatic
        fun ForceSync.toDto() =
            ForceSyncDto(
                forceRestarted = forceRestarted,
            )
    }
}
