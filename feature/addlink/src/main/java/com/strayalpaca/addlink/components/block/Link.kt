package com.strayalpaca.addlink.components.block

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.strayalpaca.addlink.R
import com.strayalpaca.addlink.model.Link
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable

@Composable
internal fun Link(
    link: Link,
    title: String?,
    modifier: Modifier = Modifier,
    openWebBrowserByClick: Boolean = true,
) {
    val uriHandler = LocalUriHandler.current
    val placeHolder = stringResource(id = R.string.placeholder_title)
    val linkTitle = remember(link, title) { title ?: link.title.ifEmpty { placeHolder } }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .noRippleClickable {
                if (openWebBrowserByClick) {
                    uriHandler.openUri(link.url)
                }
            }
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                color = PokitTheme.colors.borderTertiary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        AsyncImage(
            model = link.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(124.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = linkTitle,
                maxLines = 2,
                style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.textSecondary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = link.url,
                maxLines = 2,
                style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.textTertiary)
            )
        }
    }
}
