package com.wavesenterprise.sdk.node.domain.blocking.event

import com.wavesenterprise.sdk.node.domain.event.BlockchainEvent

interface BlockchainEventsIterator : Iterator<BlockchainEvent>, AutoCloseable
