package pokitmons.pokit.settings.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun SettingItem(
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 24.dp, end = 24.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
              },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = PokitTheme.typography.title3,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.icon_24_arrow_right),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
        )
    }
}
