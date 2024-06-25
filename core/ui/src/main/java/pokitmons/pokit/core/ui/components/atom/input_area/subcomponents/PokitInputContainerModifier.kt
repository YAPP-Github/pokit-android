package pokitmons.pokit.core.ui.components.atom.input_area.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.input_area.attributes.PokitInputAreaState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun Modifier.pokitInputAreaContainerModifier(
    state: PokitInputAreaState,
) : Modifier {
    val inputContainerShape = RoundedCornerShape(8.dp)
    val backgroundColor = getBackgroundColor(state = state)
    val strokeColor = getStrokeColor(state = state)

    return this then Modifier
        .clip(
            shape = inputContainerShape
        )
        .background(
            shape = inputContainerShape,
            color = backgroundColor
        )
        .border(
            shape = inputContainerShape,
            width = 1.dp,
            color = strokeColor
        )
        .padding(
            paddingValues = PaddingValues(16.dp)
        )
}

@Composable
private fun getBackgroundColor(
    state: PokitInputAreaState,
) : Color {
    return when(state) {
        PokitInputAreaState.DEFAULT -> {
            PokitTheme.colors.backgroundBase
        }
        PokitInputAreaState.INPUT -> {
            PokitTheme.colors.backgroundBase
        }
        PokitInputAreaState.ACTIVE -> {
            PokitTheme.colors.backgroundBase
        }
        PokitInputAreaState.DISABLE -> {
            PokitTheme.colors.backgroundDisable
        }
        PokitInputAreaState.READ_ONLY -> {
            PokitTheme.colors.backgroundSecondary
        }
        PokitInputAreaState.ERROR -> {
            PokitTheme.colors.backgroundBase
        }
    }
}

@Composable
private fun getStrokeColor(
    state: PokitInputAreaState,
) : Color {
    return when(state) {
        PokitInputAreaState.DEFAULT -> {
            PokitTheme.colors.borderSecondary
        }
        PokitInputAreaState.INPUT -> {
            PokitTheme.colors.borderSecondary
        }
        PokitInputAreaState.ACTIVE -> {
            PokitTheme.colors.brand
        }
        PokitInputAreaState.DISABLE -> {
            PokitTheme.colors.borderDisable
        }
        PokitInputAreaState.READ_ONLY -> {
            PokitTheme.colors.borderSecondary
        }
        PokitInputAreaState.ERROR -> {
            PokitTheme.colors.error
        }
    }
}
