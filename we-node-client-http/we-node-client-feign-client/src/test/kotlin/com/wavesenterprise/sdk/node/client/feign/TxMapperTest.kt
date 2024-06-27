package com.wavesenterprise.sdk.node.client.feign

import com.wavesenterprise.sdk.node.client.http.DataEntryDto
import com.wavesenterprise.sdk.node.client.http.DataEntryDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.DataValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Base64

class TxMapperTest {

    @Test
    fun `should correct map to BooleanDataValue`() {
        DataEntryDto(
            key = "bool",
            type = "boolean",
            value = true,
        ).toDomain().also {
            assertTrue(it.value is DataValue.BooleanDataValue)
        }
    }

    @Test
    fun `should correct map to StringDataValue`() {
        DataEntryDto(
            key = "str",
            type = "string",
            value = "test",
        ).toDomain().also {
            assertTrue(it.value is DataValue.StringDataValue)
        }
    }

    @Test
    fun `should correct map to IntegerDataValue`() {
        DataEntryDto(
            key = "int",
            type = "integer",
            value = 1,
        ).toDomain().also {
            assertTrue(it.value is DataValue.IntegerDataValue)
        }
    }

    @Test
    fun `should correct map to BinaryDataValue`() {
        DataEntryDto(
            key = "bin",
            type = "binary",
            value = Base64.getEncoder().encodeToString("test".toByteArray()),
        ).toDomain().also {
            assertTrue(it.value is DataValue.BinaryDataValue)
        }
    }

    @Test
    fun `should throw exception when unknown type`() {
        val incorrectBooleanDataEntryDto = DataEntryDto(
            key = "bool",
            type = "bool",
            value = true,
        )
        assertThrows<IllegalStateException> {
            incorrectBooleanDataEntryDto.toDomain()
        }.also {
            assertEquals(
                "Unknown data type ${incorrectBooleanDataEntryDto.type} for key ${incorrectBooleanDataEntryDto.key}",
                it.message,
            )
        }
    }
}
