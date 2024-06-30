package pokitmons.pokit.core.ui.components.atom.input.subcomponents.container

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputIconPosition
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputShape
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputState

@Composable
internal fun PokitInputContainer(
    iconPosition: PokitInputIconPosition?,
    modifier: Modifier = Modifier,
    shape: PokitInputShape = PokitInputShape.RECTANGLE,
    state: PokitInputState = PokitInputState.DEFAULT,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .pokitInputContainerModifier(
                iconPosition = iconPosition,
                shape = shape,
                state = state
            )
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}
