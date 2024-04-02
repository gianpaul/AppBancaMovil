package nttdata.pe.appbancamovil.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun AppBancaMovilTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = ButtonBackgroundColor,
            onPrimary = TextColor,
            background = BackgroundColor,
            onBackground = TextColor,
            secondary = ButtonBackgroundColor,
            onSecondary = TextColor
        ),
        typography = Typography,
    ) {
        content()
    }
}