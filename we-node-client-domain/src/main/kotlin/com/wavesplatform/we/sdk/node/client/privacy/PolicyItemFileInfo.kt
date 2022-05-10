package com.wavesplatform.we.sdk.node.client.privacy

import com.wavesplatform.we.sdk.node.client.DataSize
import com.wavesplatform.we.sdk.node.client.Timestamp

data class PolicyItemFileInfo(
    val filename: String,
    val size: DataSize,
    val timestamp: Timestamp,
    val author: DataAuthor,
    val comment: DataComment,
)
