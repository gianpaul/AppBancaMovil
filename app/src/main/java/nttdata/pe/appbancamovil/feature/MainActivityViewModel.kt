package nttdata.pe.appbancamovil.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nttdata.pe.appbancamovil.core.util.Event
import kotlin.time.Duration.Companion.minutes

class MainActivityViewModel : ViewModel() {

    private val _eventNavigateToLogin = MutableLiveData<Event<Unit>>()
    val eventNavigateToLogin: LiveData<Event<Unit>> = _eventNavigateToLogin

    fun startSessionTimer() {
        viewModelScope.launch {
            delay(2.minutes)
            _eventNavigateToLogin.value = Event(Unit)
        }
    }
}