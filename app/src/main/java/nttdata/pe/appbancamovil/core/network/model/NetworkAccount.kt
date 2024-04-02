package nttdata.pe.appbancamovil.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAccount(
    @SerialName ("accounts") val accounts: List<NetworkAccountBank> = emptyList()
)

@Serializable
data class NetworkAccountBank(
    @SerialName ("balance") val balance: String,
    @SerialName ("currency") val currency: String,
    @SerialName ("type") val type: String,
    @SerialName ("number") val number: String,
)