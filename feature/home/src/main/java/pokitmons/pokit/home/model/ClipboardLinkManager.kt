package pokitmons.pokit.home.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.flow.asEventFlow
import java.util.Locale
import java.util.regex.Pattern

object ClipboardLinkManager {
    private val _clipboardLinkUrl: MutableEventFlow<String> = MutableEventFlow()
    val clipboardLinkUrl: EventFlow<String> = _clipboardLinkUrl.asEventFlow()

    fun setClipboardLink(linkUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _clipboardLinkUrl.emit(linkUrl)
        }
    }

    fun checkUrlIsValid(url: String): Boolean {
        val urlPattern = (
            "^(http://|https://)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}" +
                "(:[0-9]{1,5})?(/.*)?$"
            )

        val pattern = Pattern.compile(urlPattern)
        val matcher = pattern.matcher(url.lowercase(Locale.getDefault()))

        return matcher.matches()
    }
}
