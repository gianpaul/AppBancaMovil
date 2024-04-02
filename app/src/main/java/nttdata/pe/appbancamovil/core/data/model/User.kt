package nttdata.pe.appbancamovil.core.data.model

import nttdata.pe.appbancamovil.core.domain.entity.UserData
import nttdata.pe.appbancamovil.core.network.model.NetworkUserAccount

fun NetworkUserAccount.asEntity(): UserData {
    return UserData(
        id = this.id
    )
}