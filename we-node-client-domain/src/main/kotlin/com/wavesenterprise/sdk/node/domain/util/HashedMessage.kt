package com.wavesenterprise.sdk.node.domain.util

import com.wavesenterprise.sdk.node.domain.Hash

data class HashedMessage(
    val message: String,
    val hash: Hash,
)
