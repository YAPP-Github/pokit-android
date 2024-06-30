package pokitmons.pokit.core.ui.components.atom.chip.subcomponents.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipIconPosiion
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipSize
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipState
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipType

@Composable
fun PokitChipContainer(
    iconPosiion: PokitChipIconPosiion?,
    state: PokitChipState,
    size: PokitChipSize,
    type: PokitChipType,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val space = getItemSpace(size)

    Row(
        modifier = Modifier
            .pokitChipContainerModifier(
                iconPosition = iconPosiion,
                state = state,
                size = size,
                type = type,
                onClick = onClick
            )
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement
            .spacedBy(
                space = space,
                alignment = Alignment.CenterHorizontally
            )
    ) {
        content()
    }
}

private fun getItemSpace(size: PokitChipSize): Dp {
    return when (size) {
        PokitChipSize.SMALL -> {
            4.dp
        }

        PokitChipSize.MEDIUM -> {
            0.dp
        }
    }
}
