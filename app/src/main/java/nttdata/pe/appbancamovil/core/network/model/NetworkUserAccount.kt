package nttdata.pe.appbancamovil.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkUserAccount(
    @SerialName ("id")val id: String
)
