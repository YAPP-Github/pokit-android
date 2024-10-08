package pokitmons.pokit

import android.content.ClipData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.flow.asEventFlow
import pokitmons.pokit.home.model.ClipboardLinkManager
import pokitmons.pokit.home.model.PendingSharedLinkManager
import pokitmons.pokit.navigation.Login
import pokitmons.pokit.navigation.ROUTE_WITHOUT_LOGIN
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _currentRoute: MutableStateFlow<String> = MutableStateFlow(Login.route)
    val currentRoute: StateFlow<String> = _currentRoute.asStateFlow()

    private val _navigationEvent: MutableEventFlow<NavigationEvent> = MutableEventFlow()
    val navigationEvent: EventFlow<NavigationEvent> = _navigationEvent.asEventFlow()

    fun setCurrentRoute(route: String) {
        _currentRoute.update { route }
    }

    fun setClipData(clipData: ClipData): Boolean {
        if (clipData.itemCount == 0) return false
        val clipboardTextData = clipData.getItemAt(0).text.toString()

        if (!ClipboardLinkManager.checkUrlIsValid(clipboardTextData)) return false

        ClipboardLinkManager.setClipboardLink(clipboardTextData)
        return true
    }

    fun setSharedLinkUrl(url: String) {
        if (currentRoute.value in ROUTE_WITHOUT_LOGIN) {
            PendingSharedLinkManager.setSharedLink(url)
        } else {
            viewModelScope.launch {
                _navigationEvent.emit(NavigationEvent.AddLink(url))
            }
        }
    }
}

sealed class NavigationEvent {
    data class AddLink(val url: String) : NavigationEvent()
}
