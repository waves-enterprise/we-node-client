package com.wavesenterprise.sdk.node.domain.base58

import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.Arrays

object WeBase58 {

    private val ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray()
    private val ENCODED_ZERO = ALPHABET[0]

    private const val FROM = 256
    private const val TO = 58
    private const val TO_DIGIT = 0xFF

    /**
     * Encodes the given bytes as a base58 string (no checksum is appended).
     *
     * @param inputByteArray the bytes to encode
     * @return the base58-encoded string
     */
    @JvmStatic
    fun encode(inputByteArray: ByteArray): String {
        if (inputByteArray.isEmpty()) {
            return ""
        }
        // Count leading zeros.
        var zeros = 0
        while (zeros < inputByteArray.size && inputByteArray[zeros].toInt() == 0) {
            ++zeros
        }
        // Convert base-256 digits to base-58 digits (plus conversion to ASCII characters)
        val input = inputByteArray.copyOf(inputByteArray.size) // since we modify it in-place
        val encoded = CharArray(input.size * 2) // upper bound
        var outputStart = encoded.size
        var inputStart = zeros
        while (inputStart < input.size) {
            val byte = divmod(input, inputStart, FROM, TO)
            encoded[--outputStart] = ALPHABET[byte.toInt()]
            if (input[inputStart].toInt() == 0) {
                ++inputStart // optimization - skip leading zeros
            }
        }
        // Preserve exactly as many leading encoded zeros in output as there
        // were leading zeros in input.
        while (outputStart < encoded.size && encoded[outputStart] == ENCODED_ZERO) {
            ++outputStart
        }
        while (--zeros >= 0) {
            encoded[--outputStart] = ENCODED_ZERO
        }
        // Return encoded string (including encoded leading zeros).
        return String(encoded, outputStart, encoded.size - outputStart)
    }

    private fun divmod(number: ByteArray, firstDigit: Int, base: Int, divisor: Int): Byte {
        // this is just long division which accounts for the base of the input digits
        var remainder = 0
        for (i in firstDigit until number.size) {
            val digit = number[i].toInt() and TO_DIGIT
            val temp = remainder * base + digit
            number[i] = (temp / divisor).toByte()
            remainder = temp % divisor
        }
        return remainder.toByte()
    }

    /**
     * Decodes the given base58 string into the original data bytes.
     *
     * @param input the base58-encoded string to decode
     * @return the decoded data bytes. empty string if `input` is empty.
     * @throws IOException when decoding failed
     */
    @JvmStatic
    fun decode(input: String): ByteArray {
        if (input.isEmpty()) {
            return ByteArray(0)
        }

        val indexes = IntArray(128)
        Arrays.fill(indexes, -1)
        for (i in ALPHABET.indices) {
            indexes[ALPHABET[i].code] = i
        }

        // Convert the base58-encoded ASCII chars to a base58 byte sequence (base58 digits).
        val input58 = ByteArray(input.length)
        for (i in input.indices) {
            val c = input[i]
            val digit = if (c.code < 128) indexes[c.code] else -1
            if (digit < 0) {
                throw UnsupportedEncodingException("Base58 decoding failed: $digit at $i")
            }
            input58[i] = digit.toByte()
        }
        // Count leading zeros.
        var zeros = 0
        while (zeros < input58.size && input58[zeros].toInt() == 0) {
            ++zeros
        }
        // Convert base-58 digits to base-256 digits.
        val decoded = ByteArray(input.length)
        var outputStart = decoded.size
        var inputStart = zeros
        while (inputStart < input58.size) {
            decoded[--outputStart] = divmod(input58, inputStart, 58, 256)
            if (input58[inputStart].toInt() == 0) {
                ++inputStart // optimization - skip leading zeros
            }
        }
        // Ignore extra leading zeroes that were added during the calculation.
        while (outputStart < decoded.size && decoded[outputStart].toInt() == 0) {
            ++outputStart
        }
        // Return decoded data (including original number of leading zeros).
        return decoded.copyOfRange(outputStart - zeros, decoded.size)
    }
}
