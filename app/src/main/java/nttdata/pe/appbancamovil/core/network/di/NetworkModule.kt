package nttdata.pe.appbancamovil.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import nttdata.pe.appbancamovil.core.network.BancaMovilDataSource
import nttdata.pe.appbancamovil.core.network.retrofit.RetrofitNetwork
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        },
    ).build()


    @Provides
    fun providesBancaMovilDataSource(
        bancaMovilDataSourceImpl: RetrofitNetwork
    ): BancaMovilDataSource = bancaMovilDataSourceImpl

}