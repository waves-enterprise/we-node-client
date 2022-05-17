package com.wavesplatform.we.sdk.node.client.grpc.mapper

import kotlin.reflect.KMutableProperty0

object Util {
    @JvmStatic
    inline fun <T : Any, R : Any> T.convertIf(predicate: T.() -> Boolean, mapper: (T) -> R): R? =
        if (predicate()) {
            val result: R = mapper(this)
            result
        } else {
            null
        }

    @JvmStatic
    inline fun <T : Any, U, R : Any> T.convertIf(predicate: T.() -> Boolean, selector: T.() -> U, mapper: (U) -> R): R? =
        if (predicate()) {
            val value: U = selector(this)
            val result: R = mapper(value)
            result
        } else {
            null
        }

    @JvmStatic
    inline fun <T, U : Any> setIfNotNull(property: KMutableProperty0<T>, value: U?, mapper: (U) -> T) {
        if (value != null) {
            val valueToSet = mapper(value)
            property.set(valueToSet)
        }
    }
}
