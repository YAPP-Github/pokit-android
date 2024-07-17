package pokitmons.pokit.core.ui.components.atom.loginbutton

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitLoginButtonType
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitLoginResource
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    loginType: PokitLoginButtonType,
    text: String,
) {
    val loginResource: PokitLoginResource = getLoginResource(loginType)

    Surface(
        modifier = Modifier
            .height(50.dp)
            .border(
                shape = RoundedCornerShape(8.dp),
                width = 1.dp,
                color = loginResource.borderColor
            ),
        shape = RoundedCornerShape(8.dp),
        color = loginResource.backgroundColor,
        onClick = onClick
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = loginResource.iconResourceId),
                tint = loginResource.iconTintColor,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp),
                text = text,
                color = loginResource.textColor,
                style = PokitTheme.typography.label1Regular
            )
        }
    }
}

@Composable
private fun getLoginResource(loginType: PokitLoginButtonType): PokitLoginResource {
    return when (loginType) {
        PokitLoginButtonType.APPLE -> PokitLoginResource(
            iconResourceId = R.drawable.icon_24_apple,
            iconTintColor = PokitTheme.colors.inverseWh,
            textColor = PokitTheme.colors.inverseWh,
            backgroundColor = PokitTheme.colors.backgroundTertiary,
            borderColor = PokitTheme.colors.backgroundTertiary
        )

        PokitLoginButtonType.GOOGLE -> PokitLoginResource(
            iconResourceId = R.drawable.icon_24_google,
            textColor = PokitTheme.colors.textPrimary,
            backgroundColor = PokitTheme.colors.backgroundBase,
            borderColor = PokitTheme.colors.borderSecondary
        )
    }
}
