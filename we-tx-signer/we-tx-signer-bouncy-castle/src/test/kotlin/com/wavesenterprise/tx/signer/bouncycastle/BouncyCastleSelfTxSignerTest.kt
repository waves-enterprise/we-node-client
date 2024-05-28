package com.wavesenterprise.tx.signer.bouncycastle

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Alias
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Attachment
import com.wavesenterprise.sdk.node.domain.ContractApiVersion
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.DataValue
import com.wavesenterprise.sdk.node.domain.Decimals
import com.wavesenterprise.sdk.node.domain.Enabled
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.IssueTxDescription
import com.wavesenterprise.sdk.node.domain.IssueTxName
import com.wavesenterprise.sdk.node.domain.LeaseId
import com.wavesenterprise.sdk.node.domain.MajorVersion
import com.wavesenterprise.sdk.node.domain.MinorVersion
import com.wavesenterprise.sdk.node.domain.NodeName
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.PolicyDescription
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PolicyName
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Reissuable
import com.wavesenterprise.sdk.node.domain.Role
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.ValidationPolicy
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.contract.ContractName
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.sign.BurnSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CallContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreateAliasSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreateContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreatePolicySignRequest
import com.wavesenterprise.sdk.node.domain.sign.DisableContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.IssueSignRequest
import com.wavesenterprise.sdk.node.domain.sign.LeaseCancelSignRequest
import com.wavesenterprise.sdk.node.domain.sign.PermitSignRequest
import com.wavesenterprise.sdk.node.domain.sign.RegisterNodeSignRequest
import com.wavesenterprise.sdk.node.domain.sign.ReissueSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SetAssetScriptSignRequest
import com.wavesenterprise.sdk.node.domain.sign.SponsorFeeSignRequest
import com.wavesenterprise.sdk.node.domain.sign.UpdateContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.UpdatePolicySignRequest
import com.wavesenterprise.sdk.tx.signer.SelfTxSigner
import com.wavesenterprise.tx.signer.bouncycastle.gost.BouncyCastleSigner
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.Security
import java.security.spec.ECGenParameterSpec

class BouncyCastleSelfTxSignerTest {

    init {
        Security.addProvider(BouncyCastleProvider())
    }

    private lateinit var bouncyCastleSelfTxSigner: SelfTxSigner
    private lateinit var bouncyCastleSigner: BouncyCastleSigner

    private val keyPairGen = KeyPairGenerator.getInstance(
        "ECGOST3410", "BC"
    ).also {
        it.initialize(ECGenParameterSpec("GostR3410-2001-CryptoPro-A"))
    }
    private val bouncyCastlePrivateKey: PrivateKey = keyPairGen.generateKeyPair().private

    @BeforeEach
    fun init() {

        bouncyCastleSigner = BouncyCastleSigner(
            networkByte = NETWORK_BYTE,
            privateKey = bouncyCastlePrivateKey,
        )
        bouncyCastleSelfTxSigner = SelfTxSigner(
            signer = bouncyCastleSigner,
        )
    }

    @Test
    fun `should sign IssueTx`() {
        val signRequest = IssueSignRequest(
            senderAddress = Address.EMPTY,
            version = TxVersion(2),
            fee = Fee(0),
            quantity = Quantity(1),
            decimals = Decimals(1),
            description = IssueTxDescription("test".toByteArray()),
            name = IssueTxName("test".toByteArray()),
            reissuable = Reissuable(true),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign ReissueTx`() {
        val signRequest = ReissueSignRequest(
            senderAddress = Address.EMPTY,
            fee = Fee(0),
            version = TxVersion(2),
            quantity = Quantity(1),
            reissuable = Reissuable(true),
            assetId = AssetId.fromBase58("6UAMZA6RshxyPvt9W7aoWiUiB6N73yLQMMfiRQYXdWZh"),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign BurnTx`() {
        val signRequest = BurnSignRequest(
            senderAddress = Address.EMPTY,
            version = TxVersion(2),
            fee = Fee(0),
            assetId = AssetId("8bec1mhqTiveMeRTHgYr6az12XdqBBtpeV3ZpXMRHfSB".toByteArray()),
            quantity = Quantity(1),
            attachment = Attachment("test".toByteArray())
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign LeaseCanselTx`() {
        val signRequest = LeaseCancelSignRequest(
            senderAddress = Address.EMPTY,
            fee = Fee(0),
            leaseId = LeaseId(TxId.fromBase58("D2xJxvMtmNuzDxdgofd8jUBJaece57Szk9mLqyV4dHk")),
            version = TxVersion(2),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign CreateAliasTx`() {
        val signRequest = CreateAliasSignRequest(
            senderAddress = Address.EMPTY,
            alias = Alias("test"),
            fee = Fee(0),
            version = TxVersion(2),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign SponsorFeeTx`() {
        val signRequest = SponsorFeeSignRequest(
            senderAddress = Address.EMPTY,
            fee = Fee(0),
            version = TxVersion(1),
            assetId = AssetId("assetId".toByteArray()),
            enabled = Enabled(true),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign SetAssetScriptTx`() {
        val signRequest = SetAssetScriptSignRequest(
            senderAddress = Address.EMPTY,
            fee = Fee(0),
            version = TxVersion(1),
            assetId = AssetId("assetId".toByteArray())
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign PermitTx`() {
        val signRequest = PermitSignRequest(
            senderAddress = Address.EMPTY,
            fee = Fee(0),
            target = Address.fromBase58("3HgjVZvBHNaVfU7fHx9mqDXeJy4J8khadRC"),
            opType = OpType.ADD,
            role = Role.CONTRACT_DEVELOPER,
            dueTimestamp = Timestamp(1704810799915),
            version = TxVersion(1),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign CreateContractTx`() {
        val signRequest = CreateContractSignRequest(
            senderAddress = Address.EMPTY,
            image = ContractImage("registry.wvservices.com/waves-enterprise-public/contract:v3.1.0"),
            imageHash = ContractImageHash("bf06c4b02aecbea583845a3f3a5f5a12621e9fc7ae9b5c24e8f07b13fae22ace"),
            contractName = ContractName("TEST"),
            params = listOf(
                DataEntry(
                    key = DataKey("servers"),
                    value = DataValue.StringDataValue(
                        "{\"i\":1," +
                            "\"pubKey\":\"1\"," +
                            "\"description\":\"Decrypt 0\"," +
                            "\"type\":\"main\"}"
                    ),
                )
            ),
            fee = Fee(0),
            version = TxVersion(4),
            atomicBadge = null,
            validationPolicy = ValidationPolicy.Any,
            apiVersion = ContractApiVersion(
                major = MajorVersion(1),
                minor = MinorVersion(0),
            )
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign CallContractTx`() {
        val signRequest = CallContractSignRequest(
            senderAddress = Address.EMPTY,
            contractId = ContractId(TxId.fromBase58("D2xJxvMtmNuzDxdgofd8jUBJaece57Szk9mLqyV4dHk")),
            contractVersion = ContractVersion(1),
            params = listOf(
                DataEntry(
                    key = DataKey("servers"),
                    value = DataValue.StringDataValue(
                        "{\"i\":1," +
                            "\"pubKey\":\"1\"," +
                            "\"description\":\"Decrypt 0\"," +
                            "\"type\":\"main\"}"
                    ),
                )
            ),
            fee = Fee(0),
            version = TxVersion(4),
            atomicBadge = null,
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign UpdateContractTx`() {
        val signRequest = UpdateContractSignRequest(
            senderAddress = Address.EMPTY,
            version = TxVersion(2),
            fee = Fee(0),
            contractId = ContractId.fromBase58("D2xJxvMtmNuzDxdgofd8jUBJaece57Szk9mLqyV4dHk"),
            imageHash = ContractImageHash("test-hash"),
            image = ContractImage("test-image"),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign DisableContractTx`() {
        val signRequest = DisableContractSignRequest(
            senderAddress = Address.EMPTY,
            version = TxVersion(2),
            fee = Fee(0),
            contractId = ContractId.fromBase58("D2xJxvMtmNuzDxdgofd8jUBJaece57Szk9mLqyV4dHk"),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign AtomicTx`() {
        val signRequest = AtomicSignRequest(
            senderAddress = Address.EMPTY,
            version = TxVersion(2),
            txs = listOf(),
            fee = Fee(0),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign RegisterNodeTx`() {
        val signRequest = RegisterNodeSignRequest(
            version = TxVersion(2),
            senderAddress = Address.EMPTY,
            fee = Fee(0),
            opType = OpType.ADD,
            targetPublicKey = bouncyCastleSigner.getPublicKey(),
            nodeName = NodeName("test"),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign CreatePolicyTx`() {
        val signRequest = CreatePolicySignRequest(
            senderAddress = Address.EMPTY,
            version = TxVersion(2),
            fee = Fee(0),
            policyName = PolicyName("test name"),
            description = PolicyDescription("test description"),
            recipients = listOf(Address.fromBase58("3HgjVZvBHNaVfU7fHx9mqDXeJy4J8khadRC")),
            owners = listOf(Address.fromBase58("3HgjVZvBHNaVfU7fHx9mqDXeJy4J8khadRC")),
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    @Test
    fun `should sign UpdatePolicyTx`() {
        val signRequest = UpdatePolicySignRequest(
            senderAddress = Address.EMPTY,
            version = TxVersion(2),
            fee = Fee(0),
            recipients = listOf(Address.fromBase58("3HgjVZvBHNaVfU7fHx9mqDXeJy4J8khadRC")),
            owners = listOf(Address.fromBase58("3HgjVZvBHNaVfU7fHx9mqDXeJy4J8khadRC")),
            opType = OpType.ADD,
            policyId = PolicyId(TxId.fromBase58("D2xJxvMtmNuzDxdgofd8jUBJaece57Szk9mLqyV4dHk"))
        )

        val bouncyCastleSignedTx = bouncyCastleSelfTxSigner.sign(signRequest)
        assertNotNull(bouncyCastleSignedTx.proofs)
        assertTrue(bouncyCastleSignedTx.proofs!!.isNotEmpty())
        assertFalse(bouncyCastleSignedTx.senderAddress == Address.EMPTY)
        assertFalse(bouncyCastleSignedTx.id == TxId.EMPTY)
    }

    companion object {
        private const val NETWORK_BYTE = 'I'.code.toByte()
    }
}
