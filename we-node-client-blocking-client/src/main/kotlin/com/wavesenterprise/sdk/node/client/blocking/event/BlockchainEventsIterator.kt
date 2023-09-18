package com.wavesenterprise.sdk.node.client.blocking.event

import com.wavesenterprise.sdk.node.domain.event.BlockchainEvent

interface BlockchainEventsIterator : Iterator<BlockchainEvent>, AutoCloseable
