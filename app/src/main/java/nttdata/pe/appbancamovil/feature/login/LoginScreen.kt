package nttdata.pe.appbancamovil.feature.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nttdata.pe.appbancamovil.R
import nttdata.pe.appbancamovil.core.ui.component.ErrorDialog

@Composable
fun LoginRoute(
    navigationToProducts: (String) -> Unit, loginViewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by loginViewModel.loginState.collectAsState(initial = LoginState.Initial)

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LoginState.Login -> {
                Log.d("LoginRoute", "LoginState.Login")
                navigationToProducts(state.userId)
            }
            else -> Unit
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        LoginScreen(
            onLoading = uiState is LoginState.Loading, onLogin = loginViewModel::login
        )

        when (uiState) {
            LoginState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            LoginState.Error -> {
                Log.d("LoginRoute", "LoginState.Error")
                ErrorDialog(
                    message = "Ocurrio un error desconocido",
                    onDismissRequest = loginViewModel::resetState
                )
            }

            LoginState.NotFoundUser -> {
                Log.d("LoginRoute", "LoginState.NotFoundUser")
                ErrorDialog(
                    message = "Usuario y/o contraseña incorrectos",
                    onDismissRequest = loginViewModel::resetState
                )
            }

            else -> Unit
        }
    }

}

@Composable
fun LoginScreen(
    onLoading: Boolean, onLogin: (String, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }

        Image(
            modifier = Modifier.fillMaxWidth(0.5f),
            painter = painterResource(id = R.drawable.interbank_logo),
            contentDescription = "Logo"
        )

        OutlinedTextField(value = username,
            enabled = !onLoading,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f),
            onValueChange = { newValue ->
                if (!containsEmoji(newValue)) {
                    username = newValue
                }
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            label = {
                Text(
                    text = stringResource(id = R.string.username_hint_outlined_text_field_login),
                    style = MaterialTheme.typography.bodyLarge
                )
            })

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = password,
            enabled = !onLoading,
            onValueChange = {
                password = it
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(0.8f),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            label = {
                Text(
                    text = stringResource(id = R.string.password_hint_outlined_text_field_login),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable { passwordVisibility = !passwordVisibility },
                    imageVector = if (passwordVisibility) Icons.Outlined.Info else Icons.Outlined.Lock,
                    contentDescription = "visibility icon"
                )
            })

        Spacer(modifier = Modifier.height(16.dp))

        Button(modifier = Modifier.fillMaxWidth(0.8f), onClick = {
            onLogin(username, password)
        }, enabled = !onLoading) {
            Text(
                stringResource(id = R.string.enter_button_login),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

fun containsEmoji(s: String): Boolean {
    val length = s.length
    for (i in 0 until length) {
        val type = Character.getType(s[i])
        if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
            return true
        }
    }
    return false
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onLogin = { _, _ -> }, onLoading = true)
}
