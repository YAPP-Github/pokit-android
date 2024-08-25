package pokitmons.pokit.core.ui.components.block.linkcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun<T> LinkCard(
    item: T,
    title: String,
    sub: String,
    painter: Painter,
    notRead: Boolean,
    badgeText: String,
    onClickKebab: (T) -> Unit,
    onClickItem: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .noRippleClickable { onClickItem(item) }
        ) {
            Box(
                modifier = Modifier
                    .height(94.dp)
                    .width(124.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 94.dp)
                    .padding(vertical = 3.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = title,
                            style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.textPrimary),
                            maxLines = 2,
                            minLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        IconButton(
                            onClick = { onClickKebab(item) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_24_kebab),
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = sub,
                        style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.textTertiary),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = badgeText,
                        modifier = Modifier
                            .background(
                                color = PokitTheme.colors.backgroundPrimary,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        style = PokitTheme.typography.label4.copy(color = PokitTheme.colors.textTertiary)
                    )

                    if (notRead) {
                        Text(
                            text = stringResource(id = R.string.not_read),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = PokitTheme.colors.borderTertiary,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .background(
                                    color = PokitTheme.colors.backgroundBase,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            style = PokitTheme.typography.label4.copy(color = PokitTheme.colors.textTertiary)
                        )
                    }
                }
            }
        }
    }
}
