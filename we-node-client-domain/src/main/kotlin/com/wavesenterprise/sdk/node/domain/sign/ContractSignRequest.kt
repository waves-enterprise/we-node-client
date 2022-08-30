package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.tx.Tx

interface ContractSignRequest<T : Tx> : SignRequest<T>
