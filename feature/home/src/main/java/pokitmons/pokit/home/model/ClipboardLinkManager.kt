package pokitmons.pokit.home.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object ClipboardLinkManager {
    private val _clipboardLinkUrl: MutableSharedFlow<String> = MutableSharedFlow()
    val clipboardLinkUrl: SharedFlow<String> = _clipboardLinkUrl.asSharedFlow()

    fun setClipboardLink(linkUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _clipboardLinkUrl.emit(linkUrl)
        }
    }

    fun checkUrlIsValid(url: String): Boolean {
        val isValidUrl = url.startsWith("http://") || url.startsWith("https://")
        return isValidUrl
    }
}
