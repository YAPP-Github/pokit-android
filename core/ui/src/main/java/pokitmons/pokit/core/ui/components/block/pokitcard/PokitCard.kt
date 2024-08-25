package pokitmons.pokit.core.ui.components.block.pokitcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable

@Composable
fun PokitCard(
    text: String,
    linkCount: Int,
    painter: Painter?,
    onClick: () -> Unit,
    onClickKebab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = PokitTheme.colors.backgroundPrimary,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable { onClick() }
            .padding(top = 12.dp, start = 12.dp, bottom = 8.dp, end = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                style = PokitTheme.typography.body1SemiBold.copy(color = PokitTheme.colors.textPrimary),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                onClick = { onClickKebab() },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Top)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_24_kebab),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = R.string.pokit_count_format, linkCount),
            style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.textTertiary)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .height(84.dp)
                .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.End
        ) {
            if (painter != null) {
                Image(
                    modifier = Modifier.size(84.dp),
                    contentScale = ContentScale.Crop,
                    painter = painter,
                    contentDescription = null
                )
            }
        }
    }
}
