@file:Suppress("LongParameterList", "LargeClass")

package com.wavesenterprise.sdk.node.test.data

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Attachment
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.DataSize
import com.wavesenterprise.sdk.node.domain.DataValue
import com.wavesenterprise.sdk.node.domain.DataValue.IntegerDataValue
import com.wavesenterprise.sdk.node.domain.Decimals
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.FileName
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.IssueTxDescription
import com.wavesenterprise.sdk.node.domain.IssueTxName
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PermissionOp
import com.wavesenterprise.sdk.node.domain.PolicyDescription
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PolicyName
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Reissuable
import com.wavesenterprise.sdk.node.domain.Role
import com.wavesenterprise.sdk.node.domain.Script
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.ValidationProof
import com.wavesenterprise.sdk.node.domain.address.Message
import com.wavesenterprise.sdk.node.domain.address.SignMessageRequest
import com.wavesenterprise.sdk.node.domain.address.SignMessageResponse
import com.wavesenterprise.sdk.node.domain.address.VerifyMessageSignatureRequest
import com.wavesenterprise.sdk.node.domain.address.VerifyMessageSignatureResponse
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.CallContractTransaction
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractId.Companion.contractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.contract.ContractInfo
import com.wavesenterprise.sdk.node.domain.contract.ContractName
import com.wavesenterprise.sdk.node.domain.contract.ContractTransaction
import com.wavesenterprise.sdk.node.domain.contract.ContractTxStatus
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import com.wavesenterprise.sdk.node.domain.contract.CreateContractTransaction
import com.wavesenterprise.sdk.node.domain.contract.TxStatus
import com.wavesenterprise.sdk.node.domain.privacy.Data
import com.wavesenterprise.sdk.node.domain.privacy.DataAuthor
import com.wavesenterprise.sdk.node.domain.privacy.DataComment
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemFileInfo
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreateContractSignRequest
import com.wavesenterprise.sdk.node.domain.sign.CreatePolicySignRequest
import com.wavesenterprise.sdk.node.domain.sign.UpdatePolicySignRequest
import com.wavesenterprise.sdk.node.domain.tx.AtomicInnerTx
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.sdk.node.domain.tx.BurnTx
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreateContractTx
import com.wavesenterprise.sdk.node.domain.tx.CreatePolicyTx
import com.wavesenterprise.sdk.node.domain.tx.DisableContractTx
import com.wavesenterprise.sdk.node.domain.tx.ExecutableTx
import com.wavesenterprise.sdk.node.domain.tx.ExecutedContractTx
import com.wavesenterprise.sdk.node.domain.tx.GenesisTx
import com.wavesenterprise.sdk.node.domain.tx.IssueTx
import com.wavesenterprise.sdk.node.domain.tx.LeaseTx
import com.wavesenterprise.sdk.node.domain.tx.PermitTx
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import com.wavesenterprise.sdk.node.domain.tx.ReissueTx
import com.wavesenterprise.sdk.node.domain.tx.TransferTx
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx
import com.wavesenterprise.sdk.node.domain.tx.UpdatePolicyTx
import com.wavesenterprise.sdk.node.test.data.Util.Companion.randomBytesFromUUID
import com.wavesenterprise.sdk.node.test.data.Util.Companion.randomStringBase58
import java.time.Instant
import java.util.UUID

class TestDataFactory private constructor() {

    companion object {
        @JvmStatic
        fun address(bytes: ByteArray = randomBytesFromUUID()) = Address(bytes)

        @JvmStatic
        fun txId(bytes: ByteArray = randomBytesFromUUID()) = TxId(bytes)

        @JvmStatic
        fun password(password: String = "password") = Password(password)

        @JvmStatic
        fun policyId(txId: TxId = txId()) = PolicyId(txId)

        @JvmStatic
        fun dataHash(stringBase58: String = randomStringBase58()) = Hash.fromStringBase58(stringBase58)

        @JvmStatic
        fun data(bytes: ByteArray = randomBytesFromUUID()) = Data(bytes)

        @JvmStatic
        fun fee(value: Long = 0) = Fee(value)

        @JvmStatic
        fun publicKey(bytes: ByteArray = randomBytesFromUUID()) = PublicKey(bytes)

        @JvmStatic
        fun message(value: String = "message") = Message(value)

        @JvmStatic
        fun signature(bytes: ByteArray = randomBytesFromUUID()) = Signature(bytes)

        @JvmStatic
        fun dataKey(value: String = "dataKey") = DataKey(value)

        @JvmStatic
        fun dataEntry(
            key: DataKey = dataKey(),
            value: DataValue = IntegerDataValue(0L),
        ) = DataEntry(
            key = key,
            value = value,
        )

        @JvmStatic
        fun contractTransaction(
            sender: Address,
            type: TxType = TxType.CALL_CONTRACT,
        ): ContractTransaction =
            when (type) {
                TxType.CALL_CONTRACT -> CallContractTransaction(
                    id = txId(),
                    sender = sender,
                    contractId = ContractId(txId()),
                    params = emptyList(),
                    fee = Fee(0),
                    type = type,
                    version = TxVersion.fromInt(0),
                    proof = Signature(randomBytesFromUUID()),
                    timestamp = Timestamp.fromUtcTimestamp(Instant.now().toEpochMilli()),
                    contractVersion = ContractVersion(0),
                    feeAssetId = AssetId(randomBytesFromUUID()),
                    senderPublicKey = PublicKey(randomBytesFromUUID()),
                )

                TxType.CREATE_CONTRACT -> CreateContractTransaction(
                    id = txId(),
                    sender = sender,
                    contractId = ContractId(txId()),
                    params = emptyList(),
                    fee = Fee(0),
                    type = type,
                    version = TxVersion.fromInt(0),
                    proof = Signature(randomBytesFromUUID()),
                    timestamp = Timestamp.fromUtcTimestamp(Instant.now().toEpochMilli()),
                    feeAssetId = AssetId(randomBytesFromUUID()),
                    senderPublicKey = PublicKey(randomBytesFromUUID()),
                    contractName = ContractName("test-contract-${UUID.randomUUID()}"),
                    image = ContractImage("image"),
                    imageHash = ContractImageHash("imageHash"),
                )

                else -> throw IllegalArgumentException(
                    "Only CALL_CONTRACT and CREATE_CONTRACT are allowed as transaction type",
                )
            }

        @JvmStatic
        fun callContractTx(
            id: TxId = txId(),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            contractId: ContractId = ContractId(TxId(randomBytesFromUUID())),
            params: List<DataEntry> = emptyList(),
            fee: Fee = Fee(1L),
            version: TxVersion = TxVersion(1),
            proofs: List<Signature>? = null,
            timestamp: Timestamp = Timestamp(1L),
            feeAssetId: FeeAssetId? = null,
            contractVersion: ContractVersion = ContractVersion(1),
            atomicBadge: AtomicBadge? = null,
            senderAddress: Address = Address(randomBytesFromUUID()),
        ) = CallContractTx(
            id = id,
            senderPublicKey = senderPublicKey,
            contractId = contractId,
            params = params,
            fee = fee,
            version = version,
            proofs = proofs,
            timestamp = timestamp,
            feeAssetId = feeAssetId,
            contractVersion = contractVersion,
            atomicBadge = atomicBadge,
            senderAddress = senderAddress,
        )

        @JvmStatic
        fun createContractTx(
            id: TxId = txId(),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            params: List<DataEntry> = emptyList(),
            fee: Fee = Fee(1L),
            version: TxVersion = TxVersion(1),
            proofs: List<Signature>? = null,
            timestamp: Timestamp = Timestamp(1L),
            feeAssetId: FeeAssetId? = null,
            image: ContractImage = ContractImage("ContractImage"),
            imageHash: ContractImageHash = ContractImageHash("ContractImageHash"),
            contractName: ContractName = ContractName("ContractName"),
            atomicBadge: AtomicBadge? = null,
            senderAddress: Address = Address(randomBytesFromUUID()),
        ) = CreateContractTx(
            id = id,
            senderPublicKey = senderPublicKey,
            params = params,
            fee = fee,
            version = version,
            proofs = proofs,
            timestamp = timestamp,
            feeAssetId = feeAssetId,
            image = image,
            imageHash = imageHash,
            contractName = contractName,
            atomicBadge = atomicBadge,
            senderAddress = senderAddress,
        )

        @JvmStatic
        fun updateContractTx(
            id: TxId = txId(),
            contractId: ContractId = ContractId(TxId(randomBytesFromUUID())),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            fee: Fee = Fee(1L),
            version: TxVersion = TxVersion(1),
            proofs: List<Signature>? = null,
            timestamp: Timestamp = Timestamp(1L),
            feeAssetId: FeeAssetId? = null,
            image: ContractImage = ContractImage("ContractImage"),
            imageHash: ContractImageHash = ContractImageHash("ContractImageHash"),
            atomicBadge: AtomicBadge? = null,
            senderAddress: Address = Address(randomBytesFromUUID()),
        ) = UpdateContractTx(
            id = id,
            contractId = contractId,
            senderPublicKey = senderPublicKey,
            fee = fee,
            version = version,
            proofs = proofs,
            timestamp = timestamp,
            feeAssetId = feeAssetId,
            image = image,
            imageHash = imageHash,
            atomicBadge = atomicBadge,
            senderAddress = senderAddress,
        )

        @JvmStatic
        fun disableContractTx(
            id: TxId = txId(),
            contractId: ContractId = ContractId(TxId(randomBytesFromUUID())),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            fee: Fee = Fee(1L),
            version: TxVersion = TxVersion(1),
            proofs: List<Signature>? = null,
            timestamp: Timestamp = Timestamp(1L),
            feeAssetId: FeeAssetId? = null,
            atomicBadge: AtomicBadge? = null,
            senderAddress: Address = Address(randomBytesFromUUID()),
        ) = DisableContractTx(
            id = id,
            contractId = contractId,
            senderPublicKey = senderPublicKey,
            fee = fee,
            version = version,
            proofs = proofs,
            timestamp = timestamp,
            feeAssetId = feeAssetId,
            atomicBadge = atomicBadge,
            senderAddress = senderAddress,
        )

        @JvmStatic
        fun createContractSignRequest(
            senderAddress: Address = Address.fromBase58("3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX"),
        ) = CreateContractSignRequest(
            version = TxVersion(2),

            // sender specific
            senderAddress = senderAddress,
            // only for signature by node
            password = Password("bla"),
            // ---------------

            // fee specific
            fee = Fee(0),
            feeAssetId = FeeAssetId.fromBase58("GQNK5zxqt1a6cYxnYfG4Svk6HvEjwBdeq61vP2FnNSSp"),
            // ---------------

            // contract specific
            contractName = ContractName("bla"),
            image = ContractImage("image"),
            imageHash = ContractImageHash("ContractImageHash"),
            params = listOf(),
            // -----------------
        )

        @JvmStatic
        fun policyDataHashTx(
            id: TxId = txId(),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            fee: Fee = Fee(1L),
            version: TxVersion = TxVersion(1),
            proofs: List<Signature>? = null,
            timestamp: Timestamp = Timestamp(1L),
            feeAssetId: FeeAssetId? = null,
            atomicBadge: AtomicBadge? = null,
            senderAddress: Address = Address(randomBytesFromUUID()),
            dataHash: Hash = Hash.fromStringBase58(randomStringBase58()),
            policyId: PolicyId = PolicyId.fromByteArray(randomBytesFromUUID()),
        ) = PolicyDataHashTx(
            id = id,
            senderPublicKey = senderPublicKey,
            dataHash = dataHash,
            policyId = policyId,
            timestamp = timestamp,
            fee = fee,
            feeAssetId = feeAssetId,
            atomicBadge = atomicBadge,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun updatePolicyTx(
            id: TxId = txId(),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            fee: Fee = Fee(1L),
            version: TxVersion = TxVersion(1),
            proofs: List<Signature>? = null,
            timestamp: Timestamp = Timestamp(1L),
            feeAssetId: FeeAssetId? = null,
            atomicBadge: AtomicBadge? = null,
            senderAddress: Address = Address(randomBytesFromUUID()),
            policyId: PolicyId = PolicyId.fromByteArray(randomBytesFromUUID()),
            owners: List<Address> = listOf(),
            opType: OpType = OpType.ADD,
            recipients: List<Address> = listOf(),
        ) = UpdatePolicyTx(
            id = id,
            senderPublicKey = senderPublicKey,
            policyId = policyId,
            owners = owners,
            opType = opType,
            recipients = recipients,
            timestamp = timestamp,
            fee = fee,
            feeAssetId = feeAssetId,
            atomicBadge = atomicBadge,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun createPolicyTx(
            id: TxId = txId(),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            fee: Fee = Fee(1L),
            description: PolicyDescription = PolicyDescription("PolicyDescription"),
            policyName: PolicyName = PolicyName("PolicyName"),
            version: TxVersion = TxVersion(1),
            proofs: List<Signature>? = null,
            timestamp: Timestamp = Timestamp(1L),
            feeAssetId: FeeAssetId? = null,
            atomicBadge: AtomicBadge? = null,
            senderAddress: Address = Address(randomBytesFromUUID()),
            owners: List<Address> = listOf(),
            recipients: List<Address> = listOf(),
        ) = CreatePolicyTx(
            id = id,
            senderPublicKey = senderPublicKey,
            owners = owners,
            recipients = recipients,
            description = description,
            policyName = policyName,
            timestamp = timestamp,
            fee = fee,
            feeAssetId = feeAssetId,
            atomicBadge = atomicBadge,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun atomicTx(
            id: TxId = TxId(randomBytesFromUUID()),
            txs: List<AtomicInnerTx> = listOf(),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            fee: Fee = Fee(1L),
            version: TxVersion = TxVersion(1),
            proofs: List<Signature>? = null,
            timestamp: Timestamp = Timestamp(1L),
            senderAddress: Address = Address(randomBytesFromUUID()),
        ) = AtomicTx(
            id = id,
            senderPublicKey = senderPublicKey,
            txs = txs,
            timestamp = timestamp,
            fee = fee,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun genesisTx(
            id: TxId = TxId(randomBytesFromUUID()),
            recipient: Address = Address(randomBytesFromUUID()),
            fee: Fee = Fee(1L),
            version: TxVersion = TxVersion(1),
            signature: Signature = Signature(randomBytesFromUUID()),
            amount: Amount = Amount(1L),
            timestamp: Timestamp = Timestamp(1L),
        ) = GenesisTx(
            id = id,
            recipient = recipient,
            amount = amount,
            fee = fee,
            timestamp = timestamp,
            signature = signature,
            version = version,
        )

        @JvmStatic
        fun executedContractTx(
            id: TxId = txId(),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            tx: ExecutableTx = callContractTx(),
            resultsHash: Hash = Hash(randomBytesFromUUID()),
            results: List<DataEntry> = listOf(),
            validationProofs: List<ValidationProof> = listOf(),
            version: TxVersion = TxVersion(1),
            proofs: List<Signature> = listOf(),
            timestamp: Timestamp = Timestamp(1L),
            atomicBadge: AtomicBadge? = null,
            senderAddress: Address = Address(randomBytesFromUUID()),
        ) = ExecutedContractTx(
            id = id,
            senderPublicKey = senderPublicKey,
            tx = tx,
            results = results,
            resultsHash = resultsHash,
            validationProofs = validationProofs,
            timestamp = timestamp,
            atomicBadge = atomicBadge,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun issueTx(
            id: TxId = txId(),
            chainId: ChainId = ChainId(Byte.MIN_VALUE),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            name: IssueTxName = IssueTxName(randomBytesFromUUID()),
            description: IssueTxDescription = IssueTxDescription(randomBytesFromUUID()),
            quantity: Quantity = Quantity(1),
            decimals: Decimals = Decimals(Byte.MIN_VALUE),
            reissuable: Reissuable = Reissuable(false),
            fee: Fee = Fee(1),
            timestamp: Timestamp = Timestamp(1),
            script: Script? = null,
            proofs: List<Signature>? = null,
            senderAddress: Address = address(),
            version: TxVersion = TxVersion(1),
        ) = IssueTx(
            id = id,
            chainId = chainId,
            senderPublicKey = senderPublicKey,
            name = name,
            description = description,
            quantity = quantity,
            timestamp = timestamp,
            decimals = decimals,
            reissuable = reissuable,
            script = script,
            fee = fee,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun transferTx(
            id: TxId = txId(),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            assetId: AssetId? = null,
            feeAssetId: FeeAssetId? = null,
            timestamp: Timestamp = Timestamp(1),
            amount: Amount = Amount(1),
            fee: Fee = Fee(0),
            recipient: Address = address(),
            attachment: Attachment? = null,
            atomicBadge: AtomicBadge? = null,
            proofs: List<Signature>? = null,
            senderAddress: Address = address(),
            version: TxVersion = TxVersion(1),
        ) = TransferTx(
            id = id,
            senderPublicKey = senderPublicKey,
            assetId = assetId,
            feeAssetId = feeAssetId,
            timestamp = timestamp,
            amount = amount,
            fee = fee,
            recipient = recipient,
            attachment = attachment,
            atomicBadge = atomicBadge,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun reissueTx(
            id: TxId = txId(),
            chainId: ChainId = ChainId(Byte.MIN_VALUE),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            assetId: AssetId? = null,
            quantity: Quantity = Quantity(1),
            reissuable: Reissuable = Reissuable(false),
            fee: Fee = Fee(0),
            timestamp: Timestamp = Timestamp(1),
            proofs: List<Signature>? = null,
            senderAddress: Address = address(),
            version: TxVersion = TxVersion(1),
        ) = ReissueTx(
            id = id,
            chainId = chainId,
            senderPublicKey = senderPublicKey,
            assetId = assetId,
            quantity = quantity,
            reissuable = reissuable,
            fee = fee,
            timestamp = timestamp,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun burnTx(
            id: TxId = txId(),
            chainId: ChainId = ChainId(Byte.MIN_VALUE),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            assetId: AssetId? = null,
            amount: Amount = Amount(1),
            fee: Fee = Fee(0),
            timestamp: Timestamp = Timestamp(1),
            proofs: List<Signature>? = null,
            senderAddress: Address = address(),
            version: TxVersion = TxVersion(1),
        ) = BurnTx(
            id = id,
            chainId = chainId,
            senderPublicKey = senderPublicKey,
            assetId = assetId,
            amount = amount,
            fee = fee,
            timestamp = timestamp,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun leaseTx(
            id: TxId = txId(),
            assetId: AssetId? = null,
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            recipient: Address = address(),
            amount: Amount = Amount(1),
            fee: Fee = Fee(0),
            timestamp: Timestamp = Timestamp(1),
            proofs: List<Signature>? = null,
            senderAddress: Address = address(),
            version: TxVersion = TxVersion(1),
        ) = LeaseTx(
            id = id,
            assetId = assetId,
            senderPublicKey = senderPublicKey,
            recipient = recipient,
            amount = amount,
            fee = fee,
            timestamp = timestamp,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun permitTx(
            id: TxId = txId(),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            target: Address = Address(randomBytesFromUUID()),
            version: TxVersion = TxVersion(1),
            proofs: List<Signature> = listOf(),
            timestamp: Timestamp = Timestamp(1L),
            fee: Fee = Fee(0),
            permissionOp: PermissionOp = permissionOp(),
            senderAddress: Address = Address(randomBytesFromUUID()),
        ) = PermitTx(
            id = id,
            senderPublicKey = senderPublicKey,
            target = target,
            timestamp = timestamp,
            fee = fee,
            permissionOp = permissionOp,
            proofs = proofs,
            senderAddress = senderAddress,
            version = version,
        )

        @JvmStatic
        fun permissionOp(
            opType: OpType = OpType.ADD,
            role: Role = Role.SENDER,
            timestamp: Timestamp = Timestamp(1),
            dueTimestamp: Timestamp = Timestamp(1),
        ) = PermissionOp(
            opType = opType,
            role = role,
            timestamp = timestamp,
            dueTimestamp = dueTimestamp,
        )

        @JvmStatic
        fun policyItemFileInfo(
            filename: FileName = FileName("FileName"),
            size: DataSize = DataSize(1L),
            timestamp: Timestamp = Timestamp(1L),
            author: DataAuthor = DataAuthor("DataAuthor"),
            comment: DataComment = DataComment("DataComment"),
        ) = PolicyItemFileInfo(
            filename = filename,
            size = size,
            timestamp = timestamp,
            author = author,
            comment = comment,
        )

        @JvmStatic
        fun policyItemInfoResponse(
            senderAddress: Address = Address(randomBytesFromUUID()),
            policyId: PolicyId = PolicyId.fromByteArray(randomBytesFromUUID()),
            info: PolicyItemFileInfo = policyItemFileInfo(),
            dataHash: Hash = Hash.fromStringBase58(randomStringBase58()),
        ) = PolicyItemInfoResponse(
            senderAddress = senderAddress,
            policyId = policyId,
            info = info,
            dataHash = dataHash,
        )

        @JvmStatic
        fun txInfo(
            height: Height = Height(1L),
            tx: Tx = createContractTx(),
        ) = TxInfo(
            height = height,
            tx = tx,
        )

        @JvmStatic
        fun contractTxStatus(
            senderAddress: Address = Address(randomBytesFromUUID()),
            senderPublicKey: PublicKey = PublicKey(randomBytesFromUUID()),
            txId: TxId = TxId(randomBytesFromUUID()),
            code: Int? = null,
            message: String = "Message",
            timestamp: Timestamp = Timestamp(1),
            signature: Signature = Signature(randomBytesFromUUID()),
            status: TxStatus = TxStatus.SUCCESS,
        ) = ContractTxStatus(
            senderAddress = senderAddress,
            senderPublicKey = senderPublicKey,
            txId = txId,
            code = code,
            message = message,
            timestamp = timestamp,
            signature = signature,
            status = status,
        )

        @JvmStatic
        fun sendDataRequest(
            senderAddress: Address = address(),
            policyId: PolicyId = policyId(),
            dataHash: Hash = dataHash(),
            data: Data = data(),
            info: PolicyItemFileInfo = policyItemFileInfo(),
            fee: Fee = fee(),
            feeAssetId: FeeAssetId? = null,
            atomicBadge: AtomicBadge? = null,
            password: Password = password("password"),
            broadcastTx: Boolean = true,
        ) = SendDataRequest(
            senderAddress = senderAddress,
            policyId = policyId,
            dataHash = dataHash,
            data = data,
            info = info,
            fee = fee,
            feeAssetId = feeAssetId,
            atomicBadge = atomicBadge,
            password = password,
            broadcastTx = broadcastTx,
        )

        @JvmStatic
        fun policyItemRequest(
            policyId: PolicyId = policyId(),
            dataHash: Hash = dataHash(),
        ) = PolicyItemRequest(
            policyId = policyId,
            dataHash = dataHash,
        )

        @JvmStatic
        fun createPolicySignRequest(
            version: TxVersion = TxVersion(2),
            senderAddress: Address = address(),
            password: Password = password(),
            fee: Fee = fee(),
            feeAssetId: FeeAssetId? = null,
            policyName: PolicyName = PolicyName("test-policy"),
            recipients: List<Address> = listOf(),
            owners: List<Address> = listOf(),
            description: PolicyDescription = PolicyDescription("test"),
            atomicBadge: AtomicBadge? = null,
        ) = CreatePolicySignRequest(
            version = version,
            senderAddress = senderAddress,
            password = password,
            fee = fee,
            feeAssetId = feeAssetId,
            policyName = policyName,
            recipients = recipients,
            owners = owners,
            description = description,
            atomicBadge = atomicBadge,
        )

        @JvmStatic
        fun updatePolicySignRequest(
            policyId: PolicyId = policyId(),
            opType: OpType = OpType.ADD,
            version: TxVersion = TxVersion(2),
            senderAddress: Address = address(),
            password: Password = password(),
            fee: Fee = fee(),
            feeAssetId: FeeAssetId? = null,
            recipients: List<Address> = listOf(),
            owners: List<Address> = listOf(),
            atomicBadge: AtomicBadge? = null,
        ) = UpdatePolicySignRequest(
            policyId = policyId,
            opType = opType,
            version = version,
            senderAddress = senderAddress,
            password = password,
            fee = fee,
            feeAssetId = feeAssetId,
            recipients = recipients,
            owners = owners,
            atomicBadge = atomicBadge,
        )

        @JvmStatic
        fun atomicSignRequest(
            version: TxVersion? = TxVersion(2),
            senderAddress: Address = address(),
            password: Password? = password(),
            fee: Fee = fee(),
            txs: List<AtomicInnerTx> = listOf(),
        ) = AtomicSignRequest(
            version = version,
            senderAddress = senderAddress,
            password = password,
            fee = fee,
            txs = txs,
        )

        @JvmStatic
        fun contractInfo(
            id: ContractId = txId().contractId,
            image: ContractImage = ContractImage("test-image:0.0.1"),
            imageHash: ContractImageHash = ContractImageHash("hash"),
            version: ContractVersion = ContractVersion(1),
            active: Boolean = true,
        ) = ContractInfo(
            id = id,
            image = image,
            imageHash = imageHash,
            version = version,
            active = active,
        )

        @JvmStatic
        fun signMessageRequest(
            message: Message = message(),
            password: Password = password(),
        ) = SignMessageRequest(
            message = message,
            password = password,
        )

        @JvmStatic
        fun signMessageResponse(
            message: Message = message(),
            publicKey: PublicKey = publicKey(),
            signature: Signature = signature(),
        ) = SignMessageResponse(
            message = message,
            publicKey = publicKey,
            signature = signature,
        )

        @JvmStatic
        fun verifyMessageSignatureRequest(
            message: Message = message(),
            publicKey: PublicKey = publicKey(),
            signature: Signature = signature(),
        ) = VerifyMessageSignatureRequest(
            message = message,
            publicKey = publicKey,
            signature = signature,
        )

        @JvmStatic
        fun verifyMessageSignatureResponse(
            valid: Boolean = true,
        ) = VerifyMessageSignatureResponse(
            valid = valid,
        )
    }
}
