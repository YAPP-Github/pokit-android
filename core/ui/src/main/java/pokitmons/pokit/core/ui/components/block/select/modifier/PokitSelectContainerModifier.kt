package pokitmons.pokit.core.ui.components.block.select.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.select.attributes.PokitSelectState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun Modifier.pokitSelectContentContainerModifier(
    state: PokitSelectState,
): Modifier {
    val backgroundColor = getBackgroundColor(state = state)
    val strokeColor = getStrokeColor(state = state)

    return this then Modifier
        .clip(
            shape = RoundedCornerShape(8.dp)
        )
        .background(
            color = backgroundColor,
            shape = RoundedCornerShape(8.dp)
        )
        .border(
            shape = RoundedCornerShape(8.dp),
            width = 1.dp,
            color = strokeColor
        )
}

@Composable
private fun getBackgroundColor(
    state: PokitSelectState,
): Color {
    return when (state) {
        PokitSelectState.DEFAULT -> {
            PokitTheme.colors.backgroundBase
        }
        PokitSelectState.INPUT -> {
            PokitTheme.colors.backgroundBase
        }
        PokitSelectState.DISABLE -> {
            PokitTheme.colors.backgroundDisable
        }
        PokitSelectState.READ_ONLY -> {
            PokitTheme.colors.backgroundSecondary
        }
    }
}

@Composable
private fun getStrokeColor(
    state: PokitSelectState,
): Color {
    return when (state) {
        PokitSelectState.DEFAULT -> {
            PokitTheme.colors.borderSecondary
        }
        PokitSelectState.INPUT -> {
            PokitTheme.colors.borderSecondary
        }
        PokitSelectState.DISABLE -> {
            PokitTheme.colors.borderDisable
        }
        PokitSelectState.READ_ONLY -> {
            PokitTheme.colors.borderSecondary
        }
    }
}
