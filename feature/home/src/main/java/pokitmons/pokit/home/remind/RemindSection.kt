package pokitmons.pokit.home.remind

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun RemindSection(
    title: String,
    content: @Composable () -> Unit,
) {
    Column {
        Text(
            text = title,
            style = PokitTheme.typography.title2
        )
        content()
    }
}
