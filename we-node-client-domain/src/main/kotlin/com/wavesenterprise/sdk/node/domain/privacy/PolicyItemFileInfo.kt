package com.wavesenterprise.sdk.node.domain.privacy

import com.wavesenterprise.sdk.node.domain.DataSize
import com.wavesenterprise.sdk.node.domain.FileName
import com.wavesenterprise.sdk.node.domain.Timestamp

data class PolicyItemFileInfo(
    val filename: FileName,
    val size: DataSize,
    val timestamp: Timestamp,
    val author: DataAuthor,
    val comment: DataComment,
)
