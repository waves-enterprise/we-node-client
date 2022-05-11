package com.wavesplatform.we.sdk.node.client.http.privacy

import com.wavesplatform.we.sdk.node.client.DataSize
import com.wavesplatform.we.sdk.node.client.FileName
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.privacy.DataAuthor
import com.wavesplatform.we.sdk.node.client.privacy.DataComment
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemFileInfo

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
