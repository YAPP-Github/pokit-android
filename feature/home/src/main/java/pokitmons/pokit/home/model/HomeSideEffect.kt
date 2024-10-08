package pokitmons.pokit.home.model

sealed class HomeSideEffect {
    data object NavigateToAddPokit : HomeSideEffect()
    data class NavigateToAddLink(val url: String) : HomeSideEffect()
}
