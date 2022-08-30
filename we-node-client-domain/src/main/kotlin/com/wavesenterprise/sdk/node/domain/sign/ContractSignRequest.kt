package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.tx.ContractTx

sealed interface ContractSignRequest<T : ContractTx> : SignRequest<T>
