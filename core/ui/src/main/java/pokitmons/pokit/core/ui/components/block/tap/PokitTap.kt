package pokitmons.pokit.core.ui.components.block.tap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable

@Composable
fun <T> PokitTap(
    text: String,
    data: T,
    onClick: (T) -> Unit,
    selectedItem: T,
    modifier: Modifier = Modifier,
) {
    val textStyle = getTextStyle(selected = (data == selectedItem))

    Box(
        modifier = modifier
            .noRippleClickable {
                onClick(data)
            }
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(bottom = 16.dp, top = 4.dp)
        )

        if (selectedItem == data) {
            HorizontalDivider(
                thickness = 2.dp,
                color = PokitTheme.colors.brand,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun getTextStyle(
    selected: Boolean,
): TextStyle {
    return if (selected) {
        PokitTheme.typography.label1SemiBold.copy(color = PokitTheme.colors.textSecondary)
    } else {
        PokitTheme.typography.label1Regular.copy(color = PokitTheme.colors.textTertiary)
    }
}
