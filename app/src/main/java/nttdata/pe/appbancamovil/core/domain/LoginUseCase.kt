package nttdata.pe.appbancamovil.core.domain

import nttdata.pe.appbancamovil.core.data.repository.LoginRepository
import nttdata.pe.appbancamovil.core.domain.entity.UserData
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(username: String, password: String): UserData {
        return loginRepository.login(username, password)
    }
}