package nttdata.pe.appbancamovil.core.data.repository

import nttdata.pe.appbancamovil.core.data.model.asEntity
import nttdata.pe.appbancamovil.core.domain.entity.AccountData
import nttdata.pe.appbancamovil.core.network.BancaMovilDataSource
import javax.inject.Inject

class DefaultAccountRepository @Inject constructor(
    private val dataSource: BancaMovilDataSource
) : AccountRepository {
    override suspend fun getAccounts(userId: String): List<AccountData> {
        return dataSource.getAccounts(userId).accounts.map { it.asEntity() }
    }

    override suspend fun updateAccount(userId: String): List<AccountData> {
        return dataSource.updateAccounts(userId).accounts.map { it.asEntity() }
    }
}