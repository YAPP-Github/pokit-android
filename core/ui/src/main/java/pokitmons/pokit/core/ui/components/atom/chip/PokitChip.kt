package pokitmons.pokit.core.ui.components.atom.chip

import androidx.compose.runtime.Composable
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipIconPosiion
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipSize
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipState
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipType
import pokitmons.pokit.core.ui.components.atom.chip.subcomponents.container.PokitChipContainer
import pokitmons.pokit.core.ui.components.atom.chip.subcomponents.icon.PokitChipIcon
import pokitmons.pokit.core.ui.components.atom.chip.subcomponents.text.PokitChipText

@Composable
fun<T> PokitChip(
    data: T,
    text: String,
    removeIconPosition: PokitChipIconPosiion?,
    onClickRemove: ((T) -> Unit)?,
    onClickItem: ((T) -> Unit)?,
    state: PokitChipState = PokitChipState.DEFAULT,
    size: PokitChipSize = PokitChipSize.SMALL,
    type: PokitChipType = PokitChipType.PRIMARY,
) {
    PokitChipContainer(
        iconPosiion = removeIconPosition,
        state = state,
        size = size,
        type = type,
        onClick = { onClickItem?.invoke(data) }
    ) {
        if (removeIconPosition == PokitChipIconPosiion.LEFT) {
            PokitChipIcon(state = state, size = size, onClick = { onClickRemove?.invoke(data) })
        }

        PokitChipText(text = text, state = state, size = size)

        if (removeIconPosition == PokitChipIconPosiion.RIGHT) {
            PokitChipIcon(state = state, size = size, onClick = { onClickRemove?.invoke(data) })
        }
    }
}
