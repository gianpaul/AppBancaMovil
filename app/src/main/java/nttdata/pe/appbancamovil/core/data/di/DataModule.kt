package nttdata.pe.appbancamovil.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nttdata.pe.appbancamovil.core.data.repository.AccountRepository
import nttdata.pe.appbancamovil.core.data.repository.DefaultAccountRepository
import nttdata.pe.appbancamovil.core.data.repository.DefaultLoginRepository
import nttdata.pe.appbancamovil.core.data.repository.LoginRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsLoginRepository(
        loginRepositoryImpl: DefaultLoginRepository
    ): LoginRepository

    @Binds
    abstract fun bindsAccountRepository(
        accountRepositoryImpl: DefaultAccountRepository
    ): AccountRepository

}
