package pokitmons.pokit.core.feature.navigation.args

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object LinkUpdateEvent {
    private val _updatedLink = MutableSharedFlow<LinkArg>()
    val updatedLink = _updatedLink.asSharedFlow()

    fun modifySuccess(link: LinkArg) {
        CoroutineScope(Dispatchers.Default).launch {
            _updatedLink.emit(link)
        }
    }
}
