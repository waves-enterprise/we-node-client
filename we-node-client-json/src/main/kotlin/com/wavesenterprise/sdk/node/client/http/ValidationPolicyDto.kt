package com.wavesenterprise.sdk.node.client.http

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ValidationPolicy

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = AnyValidationPolicyDto::class, name = "any"),
    JsonSubTypes.Type(value = MajorityValidationPolicyDto::class, name = "majority"),
    JsonSubTypes.Type(value = MajorityWithOneOfValidationPolicyDto::class, name = "majorityWithOneOf"),
)
sealed interface ValidationPolicyDto {
    companion object {
        @JvmStatic
        fun ValidationPolicy.toDto(): ValidationPolicyDto =
            when (this) {
                ValidationPolicy.Any -> AnyValidationPolicyDto()
                ValidationPolicy.Majority -> MajorityValidationPolicyDto()
                is ValidationPolicy.MajorityWithOneOf -> MajorityWithOneOfValidationPolicyDto(
                    addresses = addresses.map {
                        it.asBase58String()
                    },
                )
            }

        @JvmStatic
        fun ValidationPolicyDto.toDomain(): ValidationPolicy =
            when (this) {
                is AnyValidationPolicyDto -> ValidationPolicy.Any
                is MajorityValidationPolicyDto -> ValidationPolicy.Majority
                is MajorityWithOneOfValidationPolicyDto -> ValidationPolicy.MajorityWithOneOf(
                    addresses = addresses.map {
                        Address.fromBase58(it)
                    },
                )
            }
    }
}

class AnyValidationPolicyDto : ValidationPolicyDto
class MajorityValidationPolicyDto : ValidationPolicyDto
data class MajorityWithOneOfValidationPolicyDto(
    val addresses: List<String>,
) : ValidationPolicyDto
