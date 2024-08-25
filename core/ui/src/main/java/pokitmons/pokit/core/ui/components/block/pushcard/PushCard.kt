package pokitmons.pokit.core.ui.components.block.pushcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable

@Composable
fun PushCard(
    title: String,
    sub: String,
    timeString: String,
    painter: Painter?,
    read: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val titleTextColor = getTitleTextColor(read = read)

    Row(
        modifier = modifier
            .noRippleClickable { onClick() }
            .padding(all = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (painter != null) {
            Image(
                modifier = Modifier
                    .width(94.dp)
                    .height(70.dp)
                    .clip(shape = RoundedCornerShape(2.dp)),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = PokitTheme.typography.body2Bold.copy(color = titleTextColor),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = sub,
                style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.textSecondary),
                minLines = 2,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = timeString,
                style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.textTertiary)
            )
        }
    }
}

@Composable
private fun getTitleTextColor(
    read: Boolean,
): Color {
    return if (read) {
        PokitTheme.colors.textPrimary
    } else {
        PokitTheme.colors.textDisable
    }
}
