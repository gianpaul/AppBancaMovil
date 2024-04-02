package nttdata.pe.appbancamovil.core.data.repository

import nttdata.pe.appbancamovil.core.domain.entity.AccountData

interface AccountRepository {
    suspend fun getAccounts(userId: String): List<AccountData>
    suspend fun updateAccount(userId: String): List<AccountData>
}