package pokitmons.pokit.core.ui.components.template.linkdetailbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.block.texticonbutton.TextIconButton
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.theme.color.Orange50

@Composable
fun LinkDetailBottomSheetContent(
    title: String,
    memo: String,
    bookmark: Boolean,
    pokitName: String,
    dateString: String,
    onClickBookmark: (() -> Unit)? = null,
    onClickRemoveLink: (() -> Unit)? = null,
    onClickModifyLink: (() -> Unit)? = null,
    onClickShareLink: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = PokitTheme.typography.title3.copy(color = PokitTheme.colors.textPrimary)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = pokitName,
                modifier = Modifier
                    .background(
                        color = PokitTheme.colors.backgroundPrimary,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                style = PokitTheme.typography.label4.copy(color = PokitTheme.colors.textTertiary)
            )

            Row {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = dateString,
                    style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.textTertiary),
                    textAlign = TextAlign.End
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    HorizontalDivider(
        thickness = 1.dp,
        color = PokitTheme.colors.borderTertiary
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.memo),
                style = PokitTheme.typography.body1Medium.copy(color = PokitTheme.colors.textPrimary)
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.icon_24_file),
                contentDescription = null,
                tint = PokitTheme.colors.iconPrimary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // textField?
        Text(
            text = memo,
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

    onClickBookmark?.let {
        TextIconButton(
            onClick = onClickBookmark,
            title = stringResource(id = R.string.bookmark),
            painter = painterResource(
                id = if (bookmark) R.drawable.icon_24_star else R.drawable.icon_24_star_1
            ),
            tintColor = if (bookmark) PokitTheme.colors.brand else PokitTheme.colors.iconPrimary,
            pressedTintColor = if (bookmark) PokitTheme.colors.brandLight else PokitTheme.colors.iconDisable,
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = PokitTheme.colors.borderTertiary
        )
    }

    onClickShareLink?.let {
        TextIconButton(
            onClick = onClickShareLink,
            title = stringResource(id = R.string.share),
            painter = painterResource(id = R.drawable.icon_24_share)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = PokitTheme.colors.borderTertiary
        )
    }

    onClickModifyLink?.let {
        TextIconButton(
            onClick = onClickModifyLink,
            title = stringResource(id = R.string.modify),
            painter = painterResource(id = R.drawable.icon_24_edit)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = PokitTheme.colors.borderTertiary
        )
    }

    onClickRemoveLink?.let {
        TextIconButton(
            onClick = onClickRemoveLink,
            title = stringResource(id = R.string.remove),
            painter = painterResource(id = R.drawable.icon_24_trash)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = PokitTheme.colors.borderTertiary
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
}
