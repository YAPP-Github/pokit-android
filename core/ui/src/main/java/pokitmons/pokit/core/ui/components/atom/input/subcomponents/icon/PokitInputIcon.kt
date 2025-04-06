package pokitmons.pokit.core.ui.components.atom.input.subcomponents.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun PokitInputIcon(
    state: PokitInputState,
    resourceId: Int,
    onClick: (() -> Unit)? = null,
) {
    val iconColor = getColor(state = state)
    val pressedIconColor = PokitTheme.colors.iconDisable
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Icon(
        painter = painterResource(id = resourceId),
        contentDescription = null,
        tint = if (isPressed) pressedIconColor else iconColor,
        modifier = Modifier
            .size(24.dp)
            .then(
                other = onClick?.let { method ->
                    Modifier.clickable(
                        onClick = method,
                        indication = null,
                        interactionSource = interactionSource
                    )
                } ?: Modifier
            )
    )
}

@Composable
private fun getColor(state: PokitInputState): Color {
    return when (state) {
        PokitInputState.DEFAULT -> PokitTheme.colors.iconSecondary

        PokitInputState.INPUT -> PokitTheme.colors.iconPrimary

        PokitInputState.ACTIVE -> PokitTheme.colors.iconPrimary

        PokitInputState.DISABLE -> PokitTheme.colors.iconDisable

        PokitInputState.READ_ONLY -> PokitTheme.colors.iconSecondary

        PokitInputState.ERROR -> PokitTheme.colors.error
    }
}
