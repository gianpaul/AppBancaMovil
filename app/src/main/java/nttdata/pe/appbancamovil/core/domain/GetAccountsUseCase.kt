package nttdata.pe.appbancamovil.core.domain

import nttdata.pe.appbancamovil.core.data.repository.AccountRepository
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(userId: String) = accountRepository.getAccounts(userId)
}