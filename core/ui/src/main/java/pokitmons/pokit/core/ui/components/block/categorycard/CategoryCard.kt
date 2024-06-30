package pokitmons.pokit.core.ui.components.block.categorycard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.block.categorycard.attributes.CategoryCardState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun <T> CategoryCard(
    item: T,
    painter: Painter,
    title: String,
    sub: String,
    onClickKebab: (T) -> Unit,
    onClickItem: (T) -> Unit,
    modifier: Modifier = Modifier,
    state: CategoryCardState = CategoryCardState.DISABLE,
) {
    val titleTextColor = getTitleTextColor(state = state)
    val subTextColor = getSubTextColor(state = state)

    Row(
        modifier = modifier
            .clickable(
                enabled = state != CategoryCardState.DISABLE,
                onClick = { onClickItem(item) },
            )
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = title,
                style = PokitTheme.typography.body1Bold.copy(color = titleTextColor),
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = sub,
                style = PokitTheme.typography.detail1.copy(color = subTextColor),
            )
        }

        IconButton(
            onClick = { onClickKebab(item) },
            modifier = Modifier
                .padding(0.dp)
                .align(Alignment.Top),
            enabled = state != CategoryCardState.DISABLE,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_24_kebab),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
private fun getTitleTextColor(
    state: CategoryCardState,
): Color {
    return when (state) {
        CategoryCardState.DEFAULT -> {
            PokitTheme.colors.textPrimary
        }

        CategoryCardState.ACTIVE -> {
            PokitTheme.colors.textPrimary
        }

        CategoryCardState.DISABLE -> {
            PokitTheme.colors.textDisable
        }
    }
}

@Composable
private fun getSubTextColor(
    state: CategoryCardState,
): Color {
    return when (state) {
        CategoryCardState.DEFAULT -> {
            PokitTheme.colors.textTertiary
        }

        CategoryCardState.ACTIVE -> {
            PokitTheme.colors.textTertiary
        }

        CategoryCardState.DISABLE -> {
            PokitTheme.colors.textDisable
        }
    }
}
