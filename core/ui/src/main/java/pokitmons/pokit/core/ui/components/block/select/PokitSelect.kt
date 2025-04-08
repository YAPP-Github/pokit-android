package pokitmons.pokit.core.ui.components.block.select

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.block.select.attributes.PokitSelectState
import pokitmons.pokit.core.ui.components.block.select.modifier.pokitSelectContentContainerModifier
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitSelect(
    text: String,
    hintText: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    readOnly: Boolean = false,
    enable: Boolean = true,
) {
    val state = remember(readOnly, enable, text) {
        getState(text = text, readOnly = readOnly, enable = enable)
    }
    val labelTextColor = getLabelTextColor(state = state)
    val contentTextColor = getContentTextColor(state = state)
    val iconTintColor = getIconTintColor(state = state)

    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = PokitTheme.typography.body2Medium.copy(color = labelTextColor)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .pokitSelectContentContainerModifier(state)
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    onClick = onClick,
                    enabled = (state != PokitSelectState.DISABLE) && (state != PokitSelectState.READ_ONLY)
                )
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text.ifEmpty { hintText },
                style = PokitTheme.typography.body3Medium.copy(contentTextColor),
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.icon_24_arrow_down),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = iconTintColor
            )
        }
    }
}

private fun getState(
    text: String,
    readOnly: Boolean,
    enable: Boolean,
): PokitSelectState {
    return when {
        !enable -> {
            PokitSelectState.DISABLE
        }

        readOnly -> {
            PokitSelectState.READ_ONLY
        }

        text.isEmpty() -> {
            PokitSelectState.DEFAULT
        }

        else -> {
            PokitSelectState.INPUT
        }
    }
}

@Composable
private fun getLabelTextColor(
    state: PokitSelectState,
): Color {
    return when (state) {
        PokitSelectState.DISABLE -> PokitTheme.colors.textDisable
        else -> PokitTheme.colors.textSecondary
    }
}

@Composable
private fun getContentTextColor(
    state: PokitSelectState,
): Color {
    return when (state) {
        PokitSelectState.DISABLE -> PokitTheme.colors.textDisable
        PokitSelectState.INPUT -> PokitTheme.colors.textSecondary
        else -> PokitTheme.colors.textTertiary
    }
}

@Composable
private fun getIconTintColor(
    state: PokitSelectState,
): Color {
    return when (state) {
        PokitSelectState.DISABLE -> PokitTheme.colors.iconDisable
        else -> PokitTheme.colors.iconSecondary
    }
}
