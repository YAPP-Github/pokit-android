package pokitmons.pokit.core.ui.components.atom.button

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitLoginButtonType
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitLoginResource
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.theme.color.Gray200
import pokitmons.pokit.core.ui.theme.color.Gray700
import pokitmons.pokit.core.ui.theme.color.Gray900

@Composable
fun PokitLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    loginType: PokitLoginButtonType,
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
                contentDescription = null,
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp),
                text = loginResource.text,
                color = loginResource.textColor,
                style = PokitTheme.typography.label3Regular,
            )
        }
    }
}

private fun getLoginResource(loginType: PokitLoginButtonType): PokitLoginResource {
    return when (loginType) {
        PokitLoginButtonType.APPLE -> PokitLoginResource(
            iconResourceId = R.drawable.icon_24_apple,
            iconTintColor = Color.White,
            textColor = Color.White,
            backgroundColor = Gray700,
            borderColor = Gray700,
            text = "Apple로 시작하기"
        )

        PokitLoginButtonType.GOOGLE -> PokitLoginResource(
            iconResourceId = R.drawable.icon_24_google,
            textColor = Gray900,
            backgroundColor = Color.White,
            borderColor = Gray200,
            text = "Google로 시작하기"
        )
    }
}
