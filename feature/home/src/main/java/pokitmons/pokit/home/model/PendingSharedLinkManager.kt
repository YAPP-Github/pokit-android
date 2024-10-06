package pokitmons.pokit.home.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.flow.asEventFlow

object PendingSharedLinkManager {
    private val _sharedLinkUrl: MutableEventFlow<String> = MutableEventFlow()
    val sharedLinkUrl: EventFlow<String> = _sharedLinkUrl.asEventFlow()

    fun setSharedLink(linkUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _sharedLinkUrl.emit(linkUrl)
        }
    }
}
