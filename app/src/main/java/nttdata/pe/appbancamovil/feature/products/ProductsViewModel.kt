package nttdata.pe.appbancamovil.feature.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nttdata.pe.appbancamovil.core.domain.GetAccountsUseCase
import nttdata.pe.appbancamovil.core.domain.UpdateAccountsUseCase
import nttdata.pe.appbancamovil.core.domain.entity.AccountData
import nttdata.pe.appbancamovil.core.network.exception.NetworkException
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val updateAccountsUseCase: UpdateAccountsUseCase
) : ViewModel() {
    private val _productsState = MutableStateFlow<ProductsState>(ProductsState.Initial)
    val productsState: Flow<ProductsState> = _productsState

    fun getAccounts(userId : String) {
        viewModelScope.launch {
            _productsState.value = ProductsState.Loading
            runCatching {
                delay(3.seconds)
                getAccountsUseCase(userId = userId)
            }.fold(onSuccess = {
                _productsState.value = ProductsState.Accounts(it)
            }, onFailure = {
                when (it) {
                    is NetworkException.NotFoundAccount -> _productsState.value =
                        ProductsState.NotFoundAccounts
                    else -> _productsState.value = ProductsState.Error
                }
            })
        }
    }

    fun updateAccounts(userId : String) {
        viewModelScope.launch {
            _productsState.value = ProductsState.Loading
            runCatching {
                delay(3.seconds)
                updateAccountsUseCase(userId = userId)
            }.fold(onSuccess = {
                _productsState.value = ProductsState.Accounts(it)
            }, onFailure = {
                when (it) {
                    is NetworkException.NotFoundUpdateAccount -> _productsState.value =
                        ProductsState.NotFoundUpdateAccounts
                    else -> _productsState.value = ProductsState.Error
                }
            })
        }
    }

    fun notData() {
        _productsState.value = ProductsState.NotData
    }
}

sealed interface ProductsState {
    object Initial : ProductsState
    object Loading : ProductsState
    data class Accounts(val accounts: List<AccountData>) : ProductsState
    object NotFoundAccounts : ProductsState
    object NotData : ProductsState
    object NotFoundUpdateAccounts : ProductsState
    object Error : ProductsState
}