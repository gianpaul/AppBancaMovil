package nttdata.pe.appbancamovil.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nttdata.pe.appbancamovil.core.ui.theme.AppBancaMovilTheme
import nttdata.pe.appbancamovil.feature.login.LoginRoute
import nttdata.pe.appbancamovil.feature.products.ProductsRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            AppBancaMovilTheme {
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginRoute(navigationToProducts = { userId ->
                            navController.navigate("products/$userId") {
                                popUpTo("login") { inclusive = true }
                            }
                            viewModel.startSessionTimer()
                        })
                    }
                    composable("products/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")
                        if (userId != null) {
                            ProductsRoute(userId = userId, navigationToDetail = {
                                navController.navigate("detail")
                            })
                        }
                    }
                }
            }
            viewModel.eventNavigateToLogin.observe(this) { event ->
                event.getContentIfNotHandled()?.let {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
    }
}