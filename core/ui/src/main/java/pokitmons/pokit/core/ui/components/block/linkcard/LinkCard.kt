package pokitmons.pokit.core.ui.components.block.linkcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import pokitmons.pokit.core.ui.components.atom.checkbox.PokitCheckbox
import pokitmons.pokit.core.ui.components.atom.checkbox.attributes.PokitCheckboxStyle
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.scaleClickable

@Composable
fun<T> LinkCard(
    modifier: Modifier = Modifier,
    item: T,
    title: String,
    sub: String,
    painter: Painter,
    notRead: Boolean,
    badgeText: String?,
    onClickItem: (T) -> Unit,
    onClickKebab: ((T) -> Unit)? = null,
    hasMemo: Boolean = false,
    hasMember: Boolean = false,
    bookmark: Boolean? = null,
    checked: Boolean? = null,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .scaleClickable(pressedScale = 0.98f) {
                    onClickItem(item)
                }
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

                checked?.let {
                    Box(
                        modifier = Modifier
                            .offset(x = 8.dp, y = 8.dp)
                            .size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        PokitCheckbox(
                            checked = it,
                            style = PokitCheckboxStyle.FILLED
                        )
                    }
                }

                bookmark?.let {
                    Box(
                        modifier = Modifier
                            .offset(x = 8.dp, y = 54.dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(PokitTheme.colors.backgroundBaseIcon),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.icon_24_star),
                            contentDescription = "bookmark",
                            tint = if (it) PokitTheme.colors.brand else PokitTheme.colors.iconDisable
                        )
                    }
                }
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

                        if (onClickKebab != null) {
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
                    if (badgeText?.isNotBlank() == true) {
                        Text(
                            text = badgeText,
                            modifier = Modifier
                                .weight(1f, fill = false)
                                .background(
                                    color = PokitTheme.colors.backgroundPrimary,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            style = PokitTheme.typography.label4.copy(color = PokitTheme.colors.textTertiary),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    if (notRead) {
                        Text(
                            text = stringResource(id = R.string.not_read),
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFFFF3EA),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            style = PokitTheme.typography.label4.copy(color = PokitTheme.colors.brand)
                        )
                    }

                    if (hasMemo) {
                        Box(
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .height(20.dp)
                                    .aspectRatio(1f)
                                    .background(
                                        color = PokitTheme.colors.backgroundPrimary,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(2.dp),
                                painter = painterResource(id = R.drawable.icon_24_file),
                                tint = PokitTheme.colors.iconSecondary,
                                contentDescription = "hasMemo"
                            )
                        }
                    }

                    if (hasMember) {
                        Box(
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .height(20.dp)
                                    .aspectRatio(1f)
                                    .background(
                                        color = PokitTheme.colors.backgroundPrimary,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(2.dp),
                                painter = painterResource(id = R.drawable.icon_24_member),
                                tint = PokitTheme.colors.iconSecondary,
                                contentDescription = "hasMemo"
                            )
                        }
                    }
                }
            }
        }
    }
}
