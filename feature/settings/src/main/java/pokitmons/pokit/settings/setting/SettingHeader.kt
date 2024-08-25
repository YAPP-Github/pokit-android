package pokitmons.pokit.settings.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable
import pokitmons.pokit.core.ui.R.drawable as DrawableResource
import pokitmons.pokit.settings.R.string as StringResource

@Composable
fun SettingHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Icon(
            painter = painterResource(id = DrawableResource.icon_24_arrow_left),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .noRippleClickable { }
                .size(24.dp)
        )

        Text(
            text = stringResource(StringResource.settings),
            style = PokitTheme.typography.title3,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
