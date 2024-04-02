package nttdata.pe.appbancamovil.core.data.repository

import nttdata.pe.appbancamovil.core.data.model.asEntity
import nttdata.pe.appbancamovil.core.domain.entity.UserData
import nttdata.pe.appbancamovil.core.network.BancaMovilDataSource
import javax.inject.Inject

class DefaultLoginRepository @Inject constructor(
    private val dataSource: BancaMovilDataSource
) : LoginRepository {

    override suspend fun login(username: String, password: String): UserData {
        return dataSource.login(username, password).asEntity()
    }
}