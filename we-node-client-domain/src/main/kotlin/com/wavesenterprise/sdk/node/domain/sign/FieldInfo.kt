package com.wavesenterprise.sdk.node.domain.sign

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class FieldInfo(
    val required: Boolean,
    val sinceVersion: Int,
    val bytesPosition: Int,
)
