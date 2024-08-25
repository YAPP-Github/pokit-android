package pokitmons.pokit.search.components.linkdetailbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.theme.color.Orange50
import pokitmons.pokit.search.model.Link

@Composable
fun LinkDetailBottomSheet(
    link: Link,
    onHideBottomSheet: () -> Unit,
    show: Boolean = false,
    onClickRemoveLink: (Link) -> Unit,
    onClickModifyLink: (Link) -> Unit,
    onClickBookmark: () -> Unit,
) {
    PokitBottomSheet(
        onHideBottomSheet = onHideBottomSheet,
        show = show
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_24_bell),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = PokitTheme.colors.brand,
                            shape = CircleShape
                        )
                        .padding(2.dp),
                    colorFilter = ColorFilter.tint(PokitTheme.colors.inverseWh)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = stringResource(id = link.linkType.textResourceId),
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = link.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = PokitTheme.typography.title3.copy(color = PokitTheme.colors.textPrimary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = link.dateString,
                style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.textTertiary),
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider(
            thickness = 1.dp,
            color = PokitTheme.colors.borderTertiary
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Link(link = link)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = link.memo,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Orange50,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp),
                style = PokitTheme.typography.body3Regular.copy(color = PokitTheme.colors.textPrimary),
                maxLines = 4,
                minLines = 4
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = PokitTheme.colors.borderTertiary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onClickBookmark
                    )
                    .padding(6.dp),
                painter = painterResource(id = R.drawable.icon_24_star),
                contentDescription = "bookmark",
                colorFilter = ColorFilter.tint(
                    color = if (link.bookmark) PokitTheme.colors.brand else PokitTheme.colors.iconTertiary
                )
            )

            Spacer(modifier = Modifier.weight(1f))

//            Image(
//                modifier = Modifier
//                    .size(36.dp)
//                    .padding(6.dp),
//                painter = painterResource(id = R.drawable.icon_24_share),
//                contentDescription = "share",
//                colorFilter = ColorFilter.tint(
//                    color = PokitTheme.colors.iconSecondary
//                )
//            )
//
//            Image(
//                modifier = Modifier
//                    .size(36.dp)
//                    .clickable(
//                        indication = null,
//                        interactionSource = remember { MutableInteractionSource() },
//                        onClick = {
//                            onClickModifyLink(link)
//                        }
//                    )
//                    .padding(6.dp),
//                painter = painterResource(id = R.drawable.icon_24_edit),
//                contentDescription = "edit",
//                colorFilter = ColorFilter.tint(
//                    color = PokitTheme.colors.iconSecondary
//                )
//            )
//
//            Image(
//                modifier = Modifier
//                    .size(36.dp)
//                    .clickable(
//                        indication = null,
//                        interactionSource = remember { MutableInteractionSource() },
//                        onClick = {
//                            onClickRemoveLink(link)
//                        }
//                    )
//                    .padding(6.dp),
//                painter = painterResource(id = R.drawable.icon_24_trash),
//                contentDescription = "remove",
//                colorFilter = ColorFilter.tint(
//                    color = PokitTheme.colors.iconSecondary
//                )
//            )
        }
    }
}
