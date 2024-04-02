package nttdata.pe.appbancamovil.core.data.model

import nttdata.pe.appbancamovil.core.domain.entity.AccountData
import nttdata.pe.appbancamovil.core.network.model.NetworkAccountBank

fun NetworkAccountBank.asEntity(): AccountData {
    return AccountData(
        typeAccount = this.type,
        formatterBalance = "${this.currency} ${this.balance}",
        number = this.number,
        currency = this.currency
    )
}