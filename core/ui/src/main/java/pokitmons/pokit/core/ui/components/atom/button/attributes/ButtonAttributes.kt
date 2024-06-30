package pokitmons.pokit.core.ui.components.atom.button.attributes

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
    val resourceId : Int,
    val position : PokitButtonIconPosition,
)

enum class PokitButtonIconPosition {
    RIGHT, LEFT,
}
