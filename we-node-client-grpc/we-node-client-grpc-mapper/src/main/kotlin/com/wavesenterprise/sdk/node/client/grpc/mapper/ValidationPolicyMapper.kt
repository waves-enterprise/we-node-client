package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ValidationPolicy
import com.wavesenterprise.transaction.protobuf.ValidationPolicyKt.majorityWithOneOf
import com.wavesenterprise.transaction.protobuf.validationPolicy
import com.wavesenterprise.transaction.protobuf.ValidationPolicy as ProtoValidationPolicy

object ValidationPolicyMapper {
    @JvmStatic
    fun ValidationPolicy.dto(): ProtoValidationPolicy =
        validationPolicy {
            when (val validationPolicy = this@dto) {
                ValidationPolicy.Any -> any = ProtoValidationPolicy.Any.getDefaultInstance()
                ValidationPolicy.Majority -> ProtoValidationPolicy.Majority.getDefaultInstance()
                is ValidationPolicy.MajorityWithOneOf -> majorityWithOneOf {
                    addresses += validationPolicy.addresses.map { it.byteString() }
                }
            }
        }

    @JvmStatic
    fun ProtoValidationPolicy.domain(): ValidationPolicy =
        when (typeCase) {
            ProtoValidationPolicy.TypeCase.ANY -> ValidationPolicy.Any
            ProtoValidationPolicy.TypeCase.MAJORITY -> ValidationPolicy.Majority
            ProtoValidationPolicy.TypeCase.MAJORITY_WITH_ONE_OF ->
                ValidationPolicy.MajorityWithOneOf(
                    majorityWithOneOf.addressesList.map {
                        Address(it.byteArray())
                    }
                )
            ProtoValidationPolicy.TypeCase.TYPE_NOT_SET, null -> error("Type not set")
        }
}
