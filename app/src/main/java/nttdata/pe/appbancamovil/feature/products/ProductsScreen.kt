package nttdata.pe.appbancamovil.feature.products

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import nttdata.pe.appbancamovil.R
import nttdata.pe.appbancamovil.core.domain.entity.AccountData
import nttdata.pe.appbancamovil.core.ui.component.ErrorDialog
import nttdata.pe.appbancamovil.core.ui.theme.BackgroundColor

@Composable
fun ProductsRoute(
    productsViewModel: ProductsViewModel = hiltViewModel(),
    userId: String,
    navigationToDetail: (AccountData) -> Unit,
) {
    val uiState by productsViewModel.productsState.collectAsState(initial = ProductsState.Initial)

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ProductsState.Accounts -> {
                ProductsScreen(accounts = (uiState as ProductsState.Accounts).accounts,
                    onRefresh = { productsViewModel.updateAccounts(userId = userId) })
            }

            ProductsState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            ProductsState.NotFoundAccounts -> {
                ErrorDialog(
                    message = "Ha ocurrido un error, vuelve a intentarlo.", onDismissRequest = {
                        productsViewModel.getAccounts(userId = userId)
                    }, messageButton = "Reintentar"
                )
            }

            ProductsState.NotFoundUpdateAccounts -> {
                ErrorDialog(
                    message = "Vuelve a intentarlo.",
                    onDismissRequest = productsViewModel::notData,
                    messageButton = "Aceptar"
                )
            }

            ProductsState.NotData -> {
                NotData()
            }

            ProductsState.Initial -> productsViewModel.getAccounts(userId = userId)
            else -> Unit
        }
    }
}

@Composable
fun ProductsScreen(
    accounts: List<AccountData>, onRefresh: () -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            coroutineScope.launch {
                onRefresh()
                isRefreshing = false
            }
        },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Productos",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn {
                items(accounts) { account ->
                    AccountItem(account = account)
                }
            }
        }
    }
}

@Composable
fun NotData() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Productos",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No se han podido cargar las cuentas, inténtelo de nuevo",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun AccountItem(account: AccountData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundColor
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.card),
                contentDescription = "Account Icon",
                modifier = Modifier.size(50.dp)
            )

            Column {
                Text(
                    text = account.typeAccount,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = account.formatterBalance,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProductsScreen() {
    ProductsScreen(accounts = listOf(
        AccountData(
            typeAccount = "Cuenta de Ahorros",
            formatterBalance = "S/ 1000.00",
            number = "123456789",
            currency = "Soles"
        ), AccountData(
            typeAccount = "Cuenta Corriente",
            formatterBalance = "S/ 1000.00",
            number = "123456789",
            currency = "Soles"
        )
    ), onRefresh = {})
}