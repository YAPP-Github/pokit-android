package pokitmons.pokit.search.components.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.theme.color.Orange100
import pokitmons.pokit.search.model.Date

@Composable
internal fun CalendarCellView(
    date: Date,
    onClick: ((Date) -> Unit)? = null,
    state: CalendarCellState = CalendarCellState.ACTIVE,
) {
    val backgroundColor = getBackgroundColor(state = state)
    val textColor = getTextColor(state = state)

    Box(
        modifier = Modifier
            .size(40.dp)
            .then(
                other = onClick?.let { method ->
                    Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { method(date) }
                    )
                } ?: Modifier
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            text = date.day.toString(),
            style = PokitTheme.typography.body1Medium.copy(color = textColor)
        )
    }
}

enum class CalendarCellState {
    INACTIVE, ACTIVE, SELECTED, IN_RANGE
}

@Composable
private fun getTextColor(state: CalendarCellState): Color {
    return when (state) {
        CalendarCellState.INACTIVE -> {
            PokitTheme.colors.textTertiary
        }
        CalendarCellState.ACTIVE -> {
            PokitTheme.colors.textSecondary
        }
        CalendarCellState.IN_RANGE -> {
            PokitTheme.colors.inverseWh
        }
        CalendarCellState.SELECTED -> {
            PokitTheme.colors.inverseWh
        }
    }
}

@Composable
private fun getBackgroundColor(state: CalendarCellState): Color {
    return when (state) {
        CalendarCellState.IN_RANGE -> {
            Orange100
        }
        CalendarCellState.SELECTED -> {
            PokitTheme.colors.brand
        }
        else -> {
            Color.Unspecified
        }
    }
}
