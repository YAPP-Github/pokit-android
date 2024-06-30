package pokitmons.pokit.core.ui.components.atom.input.subcomponents.container

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputIconPosition
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputShape
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun Modifier.pokitInputContainerModifier(
    iconPosition: PokitInputIconPosition?,
    shape: PokitInputShape,
    state: PokitInputState,
): Modifier {
    val inputContainerShape = getShape(shape = shape)
    val padding = getPadding(shape = shape, iconPosition = iconPosition)
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
            paddingValues = padding
        )
}

private fun getShape(
    shape: PokitInputShape,
): Shape {
    return when (shape) {
        PokitInputShape.RECTANGLE -> RoundedCornerShape(8.dp)
        PokitInputShape.ROUND -> RoundedCornerShape(9999.dp)
    }
}

private fun getPadding(
    shape: PokitInputShape,
    iconPosition: PokitInputIconPosition?,
): PaddingValues {
    return when {
        shape == PokitInputShape.RECTANGLE -> {
            PaddingValues(horizontal = 12.dp, vertical = 12.dp)
        }

        shape == PokitInputShape.ROUND && iconPosition == null -> {
            PaddingValues(horizontal = 20.dp, vertical = 12.dp)
        }

        shape == PokitInputShape.ROUND && iconPosition == PokitInputIconPosition.LEFT -> {
            PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        }

        else -> {
            PaddingValues(start = 20.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
        }
    }
}

@Composable
private fun getBackgroundColor(
    state: PokitInputState,
): Color {
    return when (state) {
        PokitInputState.DEFAULT -> PokitTheme.colors.backgroundBase

        PokitInputState.INPUT -> PokitTheme.colors.backgroundBase

        PokitInputState.ACTIVE -> PokitTheme.colors.backgroundBase

        PokitInputState.DISABLE -> PokitTheme.colors.backgroundDisable

        PokitInputState.READ_ONLY -> PokitTheme.colors.backgroundSecondary

        PokitInputState.ERROR -> PokitTheme.colors.backgroundBase
    }
}

@Composable
private fun getStrokeColor(
    state: PokitInputState,
): Color {
    return when (state) {
        PokitInputState.DEFAULT -> PokitTheme.colors.borderSecondary

        PokitInputState.INPUT -> PokitTheme.colors.borderSecondary

        PokitInputState.ACTIVE -> PokitTheme.colors.brand

        PokitInputState.DISABLE -> PokitTheme.colors.borderDisable

        PokitInputState.READ_ONLY -> PokitTheme.colors.borderSecondary

        PokitInputState.ERROR -> PokitTheme.colors.error
    }
}
