package nttdata.pe.appbancamovil.core.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    title: String = "Se presentó un error",
    message: String,
    messageButton: String = "OK",
    onDismissRequest: () -> Unit
) {
    AlertDialog(onDismissRequest = { onDismissRequest() },
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                Text(messageButton)
            }
        })
}