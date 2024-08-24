package pokitmons.pokit.core.ui.components.template.pokkierror

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun ErrorPokki(
    modifier: Modifier = Modifier,
    pokkiSize : Dp = 180.dp,
    title: String,
    sub: String,
    onClickRetry: (() -> Unit)? = null
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
                    .height(pokkiSize)
                    .width(pokkiSize),
                painter = painterResource(id = R.drawable.cry_pokki),
                contentDescription = "empty"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = title, style = PokitTheme.typography.title2.copy(color = PokitTheme.colors.textPrimary))

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = sub, style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary))

            onClickRetry?.let { onClick ->
                Spacer(modifier = Modifier.height(16.dp))

                PokitButton(
                    type = PokitButtonType.SECONDARY,
                    size = PokitButtonSize.SMALL,
                    style = PokitButtonStyle.DEFAULT,
                    text = stringResource(id = R.string.retry),
                    icon = null,
                    onClick = onClick
                )
            }
        }
    }
}
