package com.strayalpaca.addlink.model

import com.strayalpaca.addlink.R
import pokitmons.pokit.domain.model.link.Link as DomainLink

data class AddLinkScreenState(
    val link: Link? = null,
    val title: String = "",
    val linkUrl: String = "",
    val memo: String = "",
    val memoMaxLength: Int = DomainLink.MEMO_MAX_LENGTH,
    val currentPokit: Pokit? = null,
    val useRemind: Boolean = false,
    val step: ScreenStep = ScreenStep.IDLE,
    val toastMessage: ToastMessageEvent? = null,
    val linkUpdateType: LinkUpdateType = LinkUpdateType.Create,
) {
    val isModifyLink get() = (linkUpdateType is LinkUpdateType.Modify)
}

sealed class LinkUpdateType {
    data class Modify(val linkId: Int) : LinkUpdateType()
    data object Create : LinkUpdateType()
}

sealed class ScreenStep {
    data object LOADING : ScreenStep()
    data object IDLE : ScreenStep()
    data object LINK_LOADING : ScreenStep()
    data object POKIT_SELECT : ScreenStep()
    data object POKIT_ADD_LOADING : ScreenStep()
    data object SAVE_LOADING : ScreenStep()
}

enum class ToastMessageEvent(val stringResourceId: Int) {
    NETWORK_ERROR(R.string.network_error),
    CANNOT_CREATE_POKIT_MORE(R.string.toast_cannot_create_pokit),
}
