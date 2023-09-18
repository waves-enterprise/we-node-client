package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.ValidationProof
import com.wavesenterprise.transaction.protobuf.validationProof
import com.wavesenterprise.transaction.protobuf.ValidationProofOuterClass.ValidationProof as ProtoValidationProof

object ValidationProofMapper {
    @JvmStatic
    fun ValidationProof.dto(): ProtoValidationProof =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(validationProof: ValidationProof): ProtoValidationProof =
        validationProof {
            validatorPublicKey = validationProof.validatorPublicKey.byteString()
            signature = validationProof.signature.byteString()
        }

    @JvmStatic
    fun ProtoValidationProof.domain(): ValidationProof =
        domainInternal(this)

    @JvmStatic
    internal fun domainInternal(validationProof: ProtoValidationProof): ValidationProof =
        ValidationProof(
            validatorPublicKey = PublicKey(validationProof.validatorPublicKey.byteArray()),
            signature = Signature(validationProof.signature.byteArray())
        )
}
