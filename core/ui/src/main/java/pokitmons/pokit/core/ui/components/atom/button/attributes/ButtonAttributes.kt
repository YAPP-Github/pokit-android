package pokitmons.pokit.core.ui.components.atom.button.attributes

import androidx.compose.ui.graphics.Color

enum class PokitButtonShape {
    ROUND, RECTANGLE,
}

internal enum class PokitButtonState {
    DEFAULT, DISABLE,
}

enum class PokitButtonStyle {
    FILLED, STROKE,
}

enum class PokitButtonSize {
    SMALL, MIDDLE, LARGE,
}

enum class PokitButtonType {
    PRIMARY, SECONDARY,
}

data class PokitButtonIcon(
    val resourceId: Int,
    val position: PokitButtonIconPosition,
)

enum class PokitButtonIconPosition {
    RIGHT, LEFT,
}

enum class PokitLoginButtonType {
    GOOGLE, APPLE
}

data class PokitLoginResource(
    val iconResourceId: Int,
    val iconTintColor: Color = Color.Unspecified,
    val textColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
)
