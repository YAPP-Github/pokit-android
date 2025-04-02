package pokitmons.pokit.core.ui.components.template.bottomsheet.subcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun <T> PokitBottomSheetItem(
    text: String,
    resourceId: Int,
    data: T,
    onClick: (T) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val textColor = if (isPressed) PokitTheme.colors.textDisable else PokitTheme.colors.textSecondary
    val iconTintColor = if (isPressed) PokitTheme.colors.iconDisable else PokitTheme.colors.iconPrimary

    Row(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick(data)
            }
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = PokitTheme
                .typography
                .body1Medium
                .copy(color = textColor)
        )

        Image(
            painter = painterResource(id = resourceId),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(color = iconTintColor)
        )
    }
}
