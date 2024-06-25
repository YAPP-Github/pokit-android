package pokitmons.pokit.core.ui.components.atom.input_area.subcomponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pokitmons.pokit.core.ui.components.atom.input_area.attributes.PokitInputAreaState

@Composable
internal fun PokitInputAreaContainer(
    modifier : Modifier = Modifier,
    state : PokitInputAreaState = PokitInputAreaState.DEFAULT,
    content : @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .pokitInputAreaContainerModifier(
                state = state
            )
            .then(modifier),
    ) {
        content()
    }
}
