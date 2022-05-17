package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.ValidationProofOuterClass.ValidationProof as ProtoValidationProof
import com.wavesenterprise.transaction.protobuf.ValidationProofOuterClass
import com.wavesenterprise.transaction.protobuf.validationProof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.ValidationProof
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString

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