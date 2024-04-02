package nttdata.pe.appbancamovil.core.data.repository

import nttdata.pe.appbancamovil.core.domain.entity.UserData

interface LoginRepository {
    suspend fun login(username: String, password: String): UserData
}