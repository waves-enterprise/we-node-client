package com.wavesenterprise.sdk.node.client.feign.tx

import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.client.http.tx.TxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.UtxSizeDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import com.wavesenterprise.sdk.node.domain.tx.UtxSize
import com.wavesenterprise.sdk.node.exception.NodeNotFoundException
import java.util.Optional

@Suppress("UNCHECKED_CAST")
class FeignTxService(
    private val weTxApiFeign: WeTxApiFeign,
) : TxService {

    override fun <T : Tx> sign(request: SignRequest<T>): T =
        weTxApiFeign.sign(mapDto(request)).toDomain() as T

    override fun <T : Tx> signAndBroadcast(request: SignRequest<T>): T =
        weTxApiFeign.signAndBroadcast(mapDto(request)).toDomain() as T

    override fun <T : Tx> broadcast(tx: T) = weTxApiFeign.broadcast(mapDto(tx)).toDomain() as T

    override fun utxSize(): UtxSize = weTxApiFeign.utxSize().toDomain()

    override fun utxInfo(): List<Tx> = weTxApiFeign.utxTxs().map { it.toDomain() }

    @Suppress("SwallowedException")
    override fun txInfo(txId: TxId): Optional<TxInfo> =
        try {
            weTxApiFeign.txInfo(txId.asBase58String()).map {
                TxInfo(
                    height = Height(checkNotNull(it.height) { "Height should be present when getting txInfo" }),
                    tx = it.toDomain(),
                )
            }
        } catch (ex: NodeNotFoundException) {
            Optional.empty()
        }
}
