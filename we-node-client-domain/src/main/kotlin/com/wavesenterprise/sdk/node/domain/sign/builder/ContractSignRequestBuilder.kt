package com.wavesenterprise.sdk.node.domain.sign.builder

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ContractApiVersion
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.ValidationPolicy
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.contract.ContractName
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import com.wavesenterprise.sdk.node.domain.sign.CallContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreateContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.ContractTx
import kotlin.reflect.KMutableProperty0

@Suppress("TooManyFunctions")
class ContractSignRequestBuilder {
    private var builderProperties: BuilderProperties = BuilderProperties()

    private val notNullableForCreate = with(builderProperties) {
        listOf(
            ::fee,
            ::image,
            ::imageHash,
            ::contractName,
            ::params,
        )
    }

    private val notNullableForCall = with(builderProperties) {
        listOf(
            ::fee,
            ::params,
            ::contractId,
            ::contractVersion,
        )
    }

    fun version(version: TxVersion) = this.apply { builderProperties.version = version }

    fun fee(fee: Fee) = this.apply { builderProperties.fee = fee }

    fun feeAssetId(feeAssetId: FeeAssetId) = this.apply { builderProperties.feeAssetId = feeAssetId }

    fun image(image: ContractImage) = this.apply { builderProperties.image = image }

    fun imageHash(imageHash: ContractImageHash) = this.apply { builderProperties.imageHash = imageHash }

    fun contractName(contractName: ContractName) = this.apply { builderProperties.contractName = contractName }

    fun params(params: List<DataEntry>) = this.apply {
        require(params.isNotEmpty()) {
            "${builderProperties::params.name} can't be empty"
        }
        builderProperties.params = params
    }

    fun apiVersion(apiVersion: ContractApiVersion) = this.apply { builderProperties.apiVersion = apiVersion }

    fun validationPolicy(validationPolicy: ValidationPolicy) =
        this.apply { builderProperties.validationPolicy = validationPolicy }

    fun atomicBadge(atomicBadge: AtomicBadge) = this.apply { builderProperties.atomicBadge = atomicBadge }

    fun contractVersion(contractVersion: ContractVersion) =
        this.apply { builderProperties.contractVersion = contractVersion }

    fun contractId(contractId: ContractId) = this.apply { builderProperties.contractId = contractId }

    @Suppress("ThrowsCount")
    fun build(txType: TxType): SignRequest<out ContractTx> {
        return when (txType) {
            TxType.CREATE_CONTRACT -> {
                val variablesIsEqualsNull = getVariablesIsEqualsNull(notNullableForCreate)
                if (variablesIsEqualsNull.isEmpty()) {
                    with(builderProperties) {
                        CreateContractSignRequest(
                            version = version,
                            senderAddress = Address.EMPTY,
                            fee = fee!!,
                            feeAssetId = feeAssetId,
                            image = image!!,
                            imageHash = imageHash!!,
                            contractName = contractName!!,
                            params = params!!,
                            apiVersion = apiVersion,
                            validationPolicy = validationPolicy,
                            atomicBadge = atomicBadge,
                        )
                    }
                } else {
                    throw IllegalStateException(
                        "Fields: " + variablesIsEqualsNull.toString() +
                            " can not be null - for CreateContractSignRequest",
                    )
                }
            }

            TxType.CALL_CONTRACT -> {
                val variablesIsEqualsNull = getVariablesIsEqualsNull(notNullableForCall)
                if (variablesIsEqualsNull.isEmpty()) {
                    with(builderProperties) {
                        CallContractSignRequest(
                            version = version,
                            senderAddress = Address.EMPTY,
                            fee = fee!!,
                            feeAssetId = feeAssetId,
                            params = params!!,
                            atomicBadge = atomicBadge,
                            contractId = contractId!!,
                            contractVersion = contractVersion!!,
                        )
                    }
                } else {
                    throw IllegalStateException(
                        "Fields: " + variablesIsEqualsNull.toString() +
                            " can not be null - for CallContractSignRequest",
                    )
                }
            }

            else -> error("Shouldn't be here")
        }
    }

    private fun getVariablesIsEqualsNull(notNullableProperties: List<KMutableProperty0<out Any?>>) =
        buildList {
            notNullableProperties.forEach {
                if ((it.get() == null)) {
                    this.add(it.name)
                }
            }
        }

    class BuilderProperties {
        var version: TxVersion? = null
        var fee: Fee? = null
        var feeAssetId: FeeAssetId? = null
        var image: ContractImage? = null
        var imageHash: ContractImageHash? = null
        var contractName: ContractName? = null
        var params: List<DataEntry>? = null
        var apiVersion: ContractApiVersion? = null
        var validationPolicy: ValidationPolicy? = null
        var atomicBadge: AtomicBadge? = null
        var contractVersion: ContractVersion? = null
        var contractId: ContractId? = null
    }
}
