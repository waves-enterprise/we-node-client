package com.wavesenterprise.sdk.node.domain.util.processor

fun boolToBytes(value: Boolean): ByteArray = byteArrayOf(if (value) 1 else 0)

fun strToBytes(str: String): ByteArray = str.toByteArray()

fun numberToHex(num: Long): String = num.toHexString()

fun numberToHex(num: Int): String = num.toHexString()

fun Long.toHexString(): String {
    return java.lang.Long.toHexString(this)
}

fun Int.toHexString(): String {
    return Integer.toHexString(this)
}

fun numberToBytes(num: Long, length: Long? = null) = hexToBytes(numberToHex(num), length)

fun numberToBytes(num: Int, length: Long? = null) = hexToBytes(numberToHex(num), length)

@Suppress("MagicNumber")
fun hexToBytes(hex: String, length: Long? = null): ByteArray {
    var convertedHex = if (hex.length % 2 != 0) "0$hex" else hex
    if (length != null) {
        convertedHex = convertedHex.padStart((length * 2).toInt(), '0')
    }
    val size = convertedHex.length / 2
    val array = ByteArray(size)
    for (i in 0 until size) {
        val j = i * 2
        array[i] = convertedHex.substring(j, j + 2).toInt(16).toByte()
    }
    return array
}

fun concatBytes(vararg args: ByteArray): ByteArray {
    val sumLength = args.sumOf { it.size }
    val result = ByteArray(sumLength)
    var curLength = 0

    for (arg in args) {
        arg.copyInto(result, curLength)
        curLength += arg.size
    }

    return result
}
