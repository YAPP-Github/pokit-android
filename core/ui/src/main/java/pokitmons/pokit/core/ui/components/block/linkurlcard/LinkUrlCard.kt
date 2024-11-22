package pokitmons.pokit.core.ui.components.block.linkurlcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.conditional
import pokitmons.pokit.core.ui.utils.noRippleClickable
import pokitmons.pokit.core.ui.utils.shimmerEffect

@Composable
fun LinkUrlCard(
    modifier: Modifier = Modifier,
    thumbnailPainter: Painter,
    url: String,
    title: String,
    openWebBrowserByClick: Boolean,
    isLoading: Boolean = false,
) {
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .height(IntrinsicSize.Min)
            .noRippleClickable {
                if (openWebBrowserByClick && !isLoading) {
                    uriHandler.openUri(url)
                }
            }
            .border(
                width = 1.dp,
                color = PokitTheme.colors.borderTertiary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        if (isLoading) {
            Box(modifier = Modifier.width(124.dp).fillMaxHeight().shimmerEffect())
        } else {
            Image(
                painter = thumbnailPainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(124.dp).fillMaxHeight()
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().conditional(isLoading) { shimmerEffect() },
                text = title,
                maxLines = 2,
                style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.textSecondary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth().conditional(isLoading) { shimmerEffect() },
                text = url,
                maxLines = 2,
                style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.textTertiary)
            )
        }
    }
}
