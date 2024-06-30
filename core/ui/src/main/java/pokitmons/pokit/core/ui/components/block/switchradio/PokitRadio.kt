package pokitmons.pokit.core.ui.components.block.switchradio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.switchradio.attributes.PokitSwitchRadioStyle
import pokitmons.pokit.core.ui.components.block.switchradio.subcomponents.SwitchRadioItem

@Composable
fun <T> PokitSwitchRadio(
    itemList: List<T>,
    selectedItem: T,
    onClickItem: (T) -> Unit,
    getTitleFromItem: (T) -> String,
    modifier: Modifier = Modifier,
    style: PokitSwitchRadioStyle = PokitSwitchRadioStyle.FILLED,
    itemSpace: Dp = 8.dp,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(itemSpace)
    ) {
        for (item in itemList) {
            SwitchRadioItem(
                text = getTitleFromItem(item),
                data = item,
                onClickItem = { onClickItem(item) },
                style = style,
                selected = selectedItem == item,
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
