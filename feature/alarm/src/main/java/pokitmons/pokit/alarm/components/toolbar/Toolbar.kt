package pokitmons.pokit.alarm.components.toolbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.drawable as coreDrawable

@Composable
internal fun Toolbar(
    onClickBack: () -> Unit,
    title: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 12.dp)
    ) {
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterStart),
            onClick = onClickBack
        ) {
            Icon(
                painter = painterResource(id = coreDrawable.icon_24_arrow_left),
                contentDescription = "back button"
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = PokitTheme.typography.title3.copy(color = PokitTheme.colors.textPrimary)
        )
    }
}
