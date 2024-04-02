package nttdata.pe.appbancamovil.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import nttdata.pe.appbancamovil.core.network.BancaMovilDataSource
import nttdata.pe.appbancamovil.core.network.exception.NetworkException
import nttdata.pe.appbancamovil.core.network.model.NetworkAccount
import nttdata.pe.appbancamovil.core.network.model.NetworkUserAccount
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitNetworkApi {
    @POST(value = "IniciarSesion")
    suspend fun login(
        @Query("user") username: String, @Query("password") password: String
    ): Response<NetworkUserAccount>

    @GET(value = "obtenerCuentas")
    suspend fun getAccounts(
        @Query("userId") userId: String
    ): Response<NetworkAccount>

    @GET(value = "actualizarCuentas")
    suspend fun updateAccounts(
        @Query("userId") userId: String
    ): Response<NetworkAccount>
}

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : BancaMovilDataSource {

    private val networkApi =
        Retrofit.Builder().baseUrl("https://mock-tjpp-go-production.up.railway.app/interbank/").callFactory(okhttpCallFactory)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            ).build().create(RetrofitNetworkApi::class.java)

    override suspend fun login(username: String, password: String): NetworkUserAccount {
        val response = networkApi.login(username = username, password = password)
        if (!response.isSuccessful && response.code() == 404) {
            throw NetworkException.NotFoundUser
        }
        return response.body() ?: throw Throwable("Server error")
    }

    override suspend fun getAccounts(userId: String): NetworkAccount {
        val response = networkApi.getAccounts(userId = userId)
        if (!response.isSuccessful && response.code() == 404) {
            throw NetworkException.NotFoundAccount
        }
        return response.body() ?: throw Throwable("Server error")
    }

    override suspend fun updateAccounts(userId: String): NetworkAccount {
        val response = networkApi.updateAccounts(userId = userId)
        if (!response.isSuccessful && response.code() == 404) {
            throw NetworkException.NotFoundUpdateAccount
        }
        return response.body() ?: throw Throwable("Server error")
    }

}

