package pokitmons.pokit.core.ui.components.block.linkcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun<T> LinkCard(
    item : T,
    title : String,
    sub : String,
    painter : Painter,
    indicatorVisible : Boolean,
    bookmarked : Boolean,
    badgeText : String,
    onClickKebab : (T) -> Unit,
    onClickItem : (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        if (indicatorVisible) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .offset((-6).dp, (-6).dp)
                    .background(
                        color = PokitTheme.colors.brand,
                        shape = CircleShape
                    )
                    .border(
                        color = PokitTheme.colors.inverseWh,
                        width = 2.dp,
                        shape = CircleShape
                    )
                    .zIndex(1f)
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClickItem(item) }
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

                if (bookmarked) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = (-8).dp, y = (-8).dp)
                            .background(
                                color = PokitTheme.colors.backgroundBaseIcon,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_24_star),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(PokitTheme.colors.brand)
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
                            text = title,
                            style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.textPrimary),
                            maxLines = 2,
                            minLines = 2,
                        )

                        IconButton(
                            onClick = {onClickKebab(item)},
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_24_kebab),
                                contentDescription = null)
                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = sub,
                        style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.textTertiary)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

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
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun LinkCardPreview() {
    PokitTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(12.dp)
        ) {
            LinkCard(
                title = "타이틀\n컴포스는 왜 이런가",
                sub = "2024.06.25. youtube.com",
                badgeText = "텍스트",
                painter = painterResource(id = R.drawable.icon_24_link),
                indicatorVisible = true,
                bookmarked = true,
                item = 3,
                onClickKebab = { value : Int ->  },
                onClickItem = { value : Int ->  },
            )
        }
    }
}
