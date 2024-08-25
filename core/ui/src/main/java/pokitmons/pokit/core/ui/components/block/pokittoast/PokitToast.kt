package pokitmons.pokit.core.ui.components.block.pokittoast

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitToast(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (() -> Unit)? = null,
    onClickClose: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(9999.dp))
            .background(PokitTheme.colors.backgroundTertiary)
            .clickable(
                enabled = onClick != null,
                onClick = onClick ?: {}
            )
            .padding(start = 20.dp, end = 14.dp, top = 12.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.inverseWh),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        IconButton(
            onClick = onClickClose,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_24_x),
                contentDescription = null,
                tint = PokitTheme.colors.inverseWh
            )
        }
    }
}
