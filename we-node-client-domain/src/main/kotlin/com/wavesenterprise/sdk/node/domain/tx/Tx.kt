package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxType.ATOMIC
import com.wavesenterprise.sdk.node.domain.TxType.BURN
import com.wavesenterprise.sdk.node.domain.TxType.CALL_CONTRACT
import com.wavesenterprise.sdk.node.domain.TxType.CREATE_ALIAS
import com.wavesenterprise.sdk.node.domain.TxType.CREATE_CONTRACT
import com.wavesenterprise.sdk.node.domain.TxType.CREATE_POLICY
import com.wavesenterprise.sdk.node.domain.TxType.DATA
import com.wavesenterprise.sdk.node.domain.TxType.DISABLE_CONTRACT
import com.wavesenterprise.sdk.node.domain.TxType.EXECUTED_CONTRACT
import com.wavesenterprise.sdk.node.domain.TxType.GENESIS
import com.wavesenterprise.sdk.node.domain.TxType.GENESIS_PERMIT
import com.wavesenterprise.sdk.node.domain.TxType.GENESIS_REGISTER_NODE
import com.wavesenterprise.sdk.node.domain.TxType.ISSUE
import com.wavesenterprise.sdk.node.domain.TxType.LEASE
import com.wavesenterprise.sdk.node.domain.TxType.LEASE_CANCEL
import com.wavesenterprise.sdk.node.domain.TxType.MASS_TRANSFER
import com.wavesenterprise.sdk.node.domain.TxType.PERMIT
import com.wavesenterprise.sdk.node.domain.TxType.POLICY_DATA_HASH
import com.wavesenterprise.sdk.node.domain.TxType.REGISTER_NODE
import com.wavesenterprise.sdk.node.domain.TxType.REISSUE
import com.wavesenterprise.sdk.node.domain.TxType.SET_ASSET_SCRIPT
import com.wavesenterprise.sdk.node.domain.TxType.SET_SCRIPT
import com.wavesenterprise.sdk.node.domain.TxType.SPONSOR_FEE
import com.wavesenterprise.sdk.node.domain.TxType.TRANSFER
import com.wavesenterprise.sdk.node.domain.TxType.UPDATE_CONTRACT
import com.wavesenterprise.sdk.node.domain.TxType.UPDATE_POLICY
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.sign.FieldInfo
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.concatBytes
import com.wavesenterprise.sdk.node.domain.util.processor.numberToBytes
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

sealed interface Tx {
    val id: TxId
    val timestamp: Timestamp
    val version: TxVersion

    fun withId(id: TxId): Tx
    fun withProof(proof: Signature): Tx
    fun withSenderAddress(senderAddress: Address): Tx

    @Suppress("SpreadOperator")
    fun getBytes(networkByte: Byte): ByteArray {
        val txVersion = version
        val txVersionBytes = txVersion.getSignatureBytes(networkByte)
        val txTypeBytes = byteArrayOf(this.type().code.toByte())

        val fields = this::class.memberProperties
            .map {
                it.isAccessible = true
                it
            }.filter {
                it.findAnnotation<FieldInfo>() != null
            }.sortedBy {
                val annot = requireNotNull(it.findAnnotation<FieldInfo>())
                annot.bytesPosition
            }

        val allBytes: MutableList<ByteArray> = mutableListOf()

        for (field in fields) {
            val annotation = requireNotNull(field.findAnnotation<FieldInfo>())
            val value = field.javaField?.get(this)
            if (txVersion.value >= annotation.sinceVersion) {
                if (annotation.required && value == null) {
                    throw IllegalStateException("${field.name} is required for signing in tx ${this::class.simpleName}")
                }
                if (!annotation.required) {
                    if (value == null) {
                        allBytes += byteArrayOf(0)
                    } else {
                        allBytes += byteArrayOf(1)
                        allBytes += (value as SerializableToBytes).getSignatureBytes(networkByte)
                    }
                } else {
                    if (value is List<*>) {
                        if (value.isEmpty()) {
                            allBytes += byteArrayOf(0, 0)
                        } else {
                            allBytes += numberToBytes(value.size, 2)
                            value.forEach {
                                allBytes += (it as SerializableToBytes).getSignatureBytes(networkByte)
                            }
                        }
                    } else {
                        allBytes += (value as SerializableToBytes).getSignatureBytes(networkByte)
                    }
                }
            }
        }
        return concatBytes(txTypeBytes, txVersionBytes, *allBytes.toTypedArray())
    }

    companion object {

        @JvmStatic
        fun Tx.type(): TxType =
            when (this) {
                is GenesisTx -> GENESIS
                is IssueTx -> ISSUE
                is TransferTx -> TRANSFER
                is ReissueTx -> REISSUE
                is BurnTx -> BURN
                is LeaseTx -> LEASE
                is LeaseCancelTx -> LEASE_CANCEL
                is CreateAliasTx -> CREATE_ALIAS
                is MassTransferTx -> MASS_TRANSFER
                is DataTx -> DATA
                is SetScriptTx -> SET_SCRIPT
                is SponsorFeeTx -> SPONSOR_FEE
                is SetAssetScriptTx -> SET_ASSET_SCRIPT
                is GenesisPermitTx -> GENESIS_PERMIT
                is PermitTx -> PERMIT
                is CreateContractTx -> CREATE_CONTRACT
                is CallContractTx -> CALL_CONTRACT
                is ExecutedContractTx -> EXECUTED_CONTRACT
                is DisableContractTx -> DISABLE_CONTRACT
                is UpdateContractTx -> UPDATE_CONTRACT
                is GenesisRegisterNodeTx -> GENESIS_REGISTER_NODE
                is RegisterNodeTx -> REGISTER_NODE
                is CreatePolicyTx -> CREATE_POLICY
                is UpdatePolicyTx -> UPDATE_POLICY
                is PolicyDataHashTx -> POLICY_DATA_HASH
                is AtomicTx -> ATOMIC
            }
    }
}
