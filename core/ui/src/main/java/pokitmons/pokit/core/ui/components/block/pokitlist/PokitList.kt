package pokitmons.pokit.core.ui.components.block.pokitlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.pokitlist.attributes.PokitListState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun <T> PokitList(
    item: T,
    title: String,
    sub: String,
    onClickItem: (T) -> Unit,
    modifier: Modifier = Modifier,
    state: PokitListState = PokitListState.DEFAULT,
) {
    val titleTextColor = getTitleTextColor(state = state)
    val subTextColor = getSubTextColor(state = state)

    Row(
        modifier = modifier
            .clickable(
                enabled = state != PokitListState.DISABLE,
                onClick = { onClickItem(item) }
            )
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = PokitTheme.typography.body1Bold.copy(color = titleTextColor),
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = sub,
                style = PokitTheme.typography.detail1.copy(color = subTextColor)
            )
        }
    }
}

@Composable
private fun getTitleTextColor(
    state: PokitListState,
): Color {
    return when (state) {
        PokitListState.DEFAULT -> PokitTheme.colors.textPrimary
        PokitListState.ACTIVE -> PokitTheme.colors.textPrimary
        PokitListState.DISABLE -> PokitTheme.colors.textDisable
    }
}

@Composable
private fun getSubTextColor(
    state: PokitListState,
): Color {
    return when (state) {
        PokitListState.DEFAULT -> PokitTheme.colors.textTertiary
        PokitListState.ACTIVE -> PokitTheme.colors.textTertiary
        PokitListState.DISABLE -> PokitTheme.colors.textDisable
    }
}
