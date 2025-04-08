package pokitmons.pokit.core.ui.components.template.pookiempty

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.scaleClickable

@Composable
fun EmptyPooki(
    modifier: Modifier = Modifier,
    title: String,
    sub: String,
    button: EmptyPookiButton? = null,
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
                painter = painterResource(id = R.drawable.empty_pooki),
                contentDescription = "empty"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = title, style = PokitTheme.typography.title2.copy(color = PokitTheme.colors.textPrimary))

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = sub, style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary))

            button?.let { buttonInfo ->
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier.clip(shape = RoundedCornerShape(8.dp))
                        .scaleClickable { buttonInfo.onClick() }
                        .border(
                            shape = RoundedCornerShape(8.dp),
                            width = 1.dp,
                            color = PokitTheme.colors.borderSecondary
                        )
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                    text = buttonInfo.text,
                    style = PokitTheme.typography.label2Regular.copy(color = PokitTheme.colors.textPrimary)
                )
            }
        }
    }
}

data class EmptyPookiButton(val text: String, val onClick: () -> Unit)
