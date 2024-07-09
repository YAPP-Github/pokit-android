package com.strayalpaca.addlink.model

import com.strayalpaca.addlink.R

data class AddLinkScreenState(
    val link: Link? = null,
    val linkUrl: String = "",
    val title: String = "",
    val currentPokit: Pokit? = null,
    val pokitList: List<Pokit> = emptyList(),
    val memo: String = "",
    val useRemind: Boolean = false,
    val step: ScreenStep = ScreenStep.IDLE,
    val pokitAddInput: String = "",
) {
    fun saveButtonEnable(): Boolean {
        return link != null && linkUrl.isNotEmpty() && title.isNotEmpty() && step == ScreenStep.IDLE
    }
}

sealed class ScreenStep {
    data object LOADING : ScreenStep()
    data object IDLE : ScreenStep()
    data object LINK_LOADING : ScreenStep()
    data object POKIT_SELECT : ScreenStep()
    data object POKIT_ADD : ScreenStep()
    data object POKIT_ADD_LOADING : ScreenStep()
    data object SAVE_LOADING : ScreenStep()
}

sealed class AddLinkScreenSideEffect() {
    data object AddLinkSuccess : AddLinkScreenSideEffect()
    data class ToastMessage(val toastMessageEvent: ToastMessageEvent) : AddLinkScreenSideEffect()
    data object OnNavigationBack : AddLinkScreenSideEffect()
}

enum class ToastMessageEvent(val stringResourceId: Int) {
    NETWORK_ERROR(R.string.network_error),
}
