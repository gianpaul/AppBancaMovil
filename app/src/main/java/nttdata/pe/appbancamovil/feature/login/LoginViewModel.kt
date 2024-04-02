package nttdata.pe.appbancamovil.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nttdata.pe.appbancamovil.core.domain.LoginUseCase
import nttdata.pe.appbancamovil.core.network.exception.NetworkException
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: Flow<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            runCatching {
                delay(3.seconds)
                loginUseCase(username, password)
            }.fold(onSuccess = {
                _loginState.value = LoginState.Login(it.id)
            }, onFailure = {
                when (it) {
                    is NetworkException.NotFoundUser -> _loginState.value = LoginState.NotFoundUser
                    else -> _loginState.value = LoginState.Error
                }
            })
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Initial
    }
}

sealed interface LoginState {
    object Initial : LoginState
    object Loading : LoginState
    data class Login(val userId: String) : LoginState
    object NotFoundUser : LoginState
    object Error : LoginState
}