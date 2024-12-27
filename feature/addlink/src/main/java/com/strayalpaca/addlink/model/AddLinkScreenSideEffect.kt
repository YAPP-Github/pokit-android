package com.strayalpaca.addlink.model

sealed class AddLinkScreenSideEffect {
    sealed class NavigationEvent : AddLinkScreenSideEffect() {
        data object Back : NavigationEvent()
        data object AddPokit : NavigationEvent()
    }
}
