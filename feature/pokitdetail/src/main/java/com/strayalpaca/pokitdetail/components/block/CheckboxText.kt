package com.strayalpaca.pokitdetail.components.block

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.checkbox.PokitCheckbox
import pokitmons.pokit.core.ui.components.atom.checkbox.attributes.PokitCheckboxStyle
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun CheckboxText(
    checked: Boolean,
    title: String,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = remember {
                { onClick(!checked) }
            }
        )
    ) {
        PokitCheckbox(checked = checked, onClick = onClick, style = PokitCheckboxStyle.FILLED)

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary)
        )
    }
}
