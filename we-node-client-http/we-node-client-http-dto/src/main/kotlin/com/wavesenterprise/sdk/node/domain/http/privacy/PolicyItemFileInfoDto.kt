package com.wavesenterprise.sdk.node.domain.http.privacy

import com.wavesenterprise.sdk.node.domain.DataSize
import com.wavesenterprise.sdk.node.domain.FileName
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.privacy.DataAuthor
import com.wavesenterprise.sdk.node.domain.privacy.DataComment
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemFileInfo

data class PolicyItemFileInfoDto(
    val filename: String,
    val size: Long,
    val timestamp: Long,
    val author: String,
    val comment: String,
) {
    companion object {
        @JvmStatic
        fun PolicyItemFileInfo.toDto(): PolicyItemFileInfoDto =
            PolicyItemFileInfoDto(
                filename = filename.value,
                size = size.bytesCount,
                timestamp = timestamp.utcTimestampMillis,
                author = author.value,
                comment = comment.value,
            )

        @JvmStatic
        fun PolicyItemFileInfoDto.toDomain(): PolicyItemFileInfo =
            PolicyItemFileInfo(
                filename = FileName(filename),
                size = DataSize(size),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                author = DataAuthor(author),
                comment = DataComment(comment),
            )
    }
}
