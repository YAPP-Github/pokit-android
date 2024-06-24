package pokitmons.pokit.core.ui.components.atom.input.subcomponents.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitInputIcon(
    state : PokitInputState,
    resourceId : Int,
) {
    val iconColor = getColor(state = state)

    Icon(
        painter = painterResource(id = resourceId),
        contentDescription = null,
        tint = iconColor,
        modifier = Modifier.size(24.dp)
    )
}

@Composable
private fun getColor(
    state: PokitInputState,
) : Color {
    return when(state) {
        PokitInputState.DEFAULT -> {
            PokitTheme.colors.iconSecondary
        }
        PokitInputState.INPUT -> {
            PokitTheme.colors.iconPrimary
        }
        PokitInputState.ACTIVE -> {
            PokitTheme.colors.iconPrimary
        }
        PokitInputState.DISABLE -> {
            PokitTheme.colors.iconDisable
        }
        PokitInputState.READ_ONLY -> {
            PokitTheme.colors.iconSecondary
        }
        PokitInputState.ERROR -> {
            PokitTheme.colors.error
        }
    }
}
