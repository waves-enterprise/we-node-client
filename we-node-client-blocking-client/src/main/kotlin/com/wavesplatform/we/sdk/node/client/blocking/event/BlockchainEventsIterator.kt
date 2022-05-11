package com.wavesplatform.we.sdk.node.client.blocking.event

import com.wavesplatform.we.sdk.node.client.event.BlockchainEvent

interface BlockchainEventsIterator : Iterator<BlockchainEvent>, AutoCloseable
