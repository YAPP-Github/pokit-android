package pokitmons.pokit.core.ui.components.template.pooki

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun Pooki(
    modifier: Modifier = Modifier,
    title: String,
    sub: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(180.dp),
                painter = painterResource(id = R.drawable.pooki),
                contentDescription = "pooki"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = title, style = PokitTheme.typography.title2.copy(color = PokitTheme.colors.textPrimary))

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = sub, style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary))
        }
    }
}
