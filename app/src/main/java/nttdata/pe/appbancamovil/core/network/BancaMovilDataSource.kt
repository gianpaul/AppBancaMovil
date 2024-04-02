package nttdata.pe.appbancamovil.core.network

import nttdata.pe.appbancamovil.core.network.model.NetworkAccount
import nttdata.pe.appbancamovil.core.network.model.NetworkUserAccount

interface BancaMovilDataSource {

    suspend fun login(userName: String, password: String) : NetworkUserAccount
    suspend fun getAccounts(userId: String) : NetworkAccount
    suspend fun updateAccounts(userId: String) : NetworkAccount
}