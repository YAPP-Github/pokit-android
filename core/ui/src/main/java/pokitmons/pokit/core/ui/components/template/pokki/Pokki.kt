package pokitmons.pokit.core.ui.components.template.pokki

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun Pokki(
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
                    .height(180.dp)
                    .width(180.dp),
                painter = painterResource(id = R.drawable.pokki),
                contentDescription = "pokki"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = title, style = PokitTheme.typography.title2.copy(color = PokitTheme.colors.textPrimary))

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = sub, style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary))
        }
    }
}
