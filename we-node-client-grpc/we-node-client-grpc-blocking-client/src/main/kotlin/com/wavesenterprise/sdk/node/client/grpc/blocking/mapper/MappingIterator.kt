package com.wavesenterprise.sdk.node.client.grpc.blocking.mapper

class MappingIterator<T, R>(
    private val it: Iterator<T>,
    private val mapper: (T) -> R
) : Iterator<R> {
    override fun hasNext(): Boolean = it.hasNext()

    override fun next(): R = it.next().let(mapper)
}

fun <T, R> Iterator<T>.map(mapper: (T) -> R): Iterator<R> =
    MappingIterator(this, mapper)
