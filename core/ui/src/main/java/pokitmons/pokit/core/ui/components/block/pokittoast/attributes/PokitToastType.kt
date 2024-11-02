package pokitmons.pokit.core.ui.components.block.pokittoast.attributes

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

enum class PokitToastType(
    private val getColor: @Composable () -> Color,
    val iconResourceId : Int? = null,
) {
    Normal(getColor = { PokitTheme.colors.backgroundTertiary }),
    Success(getColor = { PokitTheme.colors.success }, iconResourceId = R.drawable.icon_24_check),
    Error(getColor = { PokitTheme.colors.error }),
    Warning(getColor = { PokitTheme.colors.warning });

    val color: Color @Composable get() = getColor()
}
