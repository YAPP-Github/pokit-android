package pokitmons.pokit.core.ui.components.atom.input.attributes

import pokitmons.pokit.core.ui.R

enum class PokitInputShape {
    ROUND, RECTANGLE,
}

internal enum class PokitInputState {
    DEFAULT, INPUT, ACTIVE, DISABLE, READ_ONLY, ERROR,
}

data class PokitInputIcon(
    val position : PokitInputIconPosition,
    val resourceId : Int = R.drawable.icon_24_search,
)

enum class PokitInputIconPosition {
    RIGHT, LEFT,
}

