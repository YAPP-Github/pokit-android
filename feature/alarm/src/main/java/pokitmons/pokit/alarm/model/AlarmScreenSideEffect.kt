package pokitmons.pokit.alarm.model

sealed class AlarmScreenSideEffect {
    data class NavigateToLinkModify(val linkId: String) : AlarmScreenSideEffect()
}
