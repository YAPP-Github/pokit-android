package pokitmons.pokit.core.feature.navigation.args

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object LinkUpdateEvent {
    private val _updatedLink = MutableSharedFlow<LinkArg>()
    val updatedLink = _updatedLink.asSharedFlow()

    private val _removedLink = MutableSharedFlow<Int>()
    val removedLink = _removedLink.asSharedFlow()

    private val _addedLink = MutableSharedFlow<LinkArg>()
    val addedLink = _addedLink.asSharedFlow()

    fun modifySuccess(link: LinkArg) {
        CoroutineScope(Dispatchers.Default).launch {
            _updatedLink.emit(link)
        }
    }

    fun removeSuccess(linkId: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            _removedLink.emit(linkId)
        }
    }

    fun createSuccess(link: LinkArg) {
        CoroutineScope(Dispatchers.Default).launch {
            _addedLink.emit(link)
        }
    }
}
