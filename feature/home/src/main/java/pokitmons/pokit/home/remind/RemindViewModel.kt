package pokitmons.pokit.home.remind

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.pokitdetail.model.BottomSheetType
import com.strayalpaca.pokitdetail.model.Link
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.model.NetworkState
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.home.remind.RemindResult
import pokitmons.pokit.domain.usecase.home.remind.BookMarkContentsUseCase
import pokitmons.pokit.domain.usecase.home.remind.TodayContentsUseCase
import pokitmons.pokit.domain.usecase.home.remind.UnReadContentsUseCase
import pokitmons.pokit.domain.usecase.link.DeleteLinkUseCase
import javax.inject.Inject

@HiltViewModel
class RemindViewModel @Inject constructor(
    private val unReadContentsUseCase: UnReadContentsUseCase,
    private val todayContentsUseCase: TodayContentsUseCase,
    private val bookMarkContentsUseCase: BookMarkContentsUseCase,
    private val deleteLinkUseCase: DeleteLinkUseCase,
) : ViewModel() {

    private var _unReadContents: MutableStateFlow<List<RemindResult>> = MutableStateFlow(emptyList())
    val unReadContents: StateFlow<List<RemindResult>>
        get() = _unReadContents.asStateFlow()

    private val _unReadContentNetworkState : MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.IDLE)
    val unreadContentNetworkState = _unReadContentNetworkState.asStateFlow()

    private var _todayContents: MutableStateFlow<List<RemindResult>> = MutableStateFlow(emptyList())
    val todayContents: StateFlow<List<RemindResult>>
        get() = _todayContents.asStateFlow()

    private val _todayContentsNetworkState : MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.IDLE)
    val todayContentsNetworkState = _todayContentsNetworkState.asStateFlow()

    private var _bookmarkContents: MutableStateFlow<List<RemindResult>> = MutableStateFlow(emptyList())
    val bookmarkContents: StateFlow<List<RemindResult>>
        get() = _bookmarkContents.asStateFlow()

    private val _bookmarkContentsNetworkState : MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.IDLE)
    val bookmarkContentsNetworkState = _bookmarkContentsNetworkState.asStateFlow()

    private val _currentSelectedLink = MutableStateFlow<Link?>(null)
    val currentSelectedLink = _currentSelectedLink.asStateFlow()

    private val _pokitOptionBottomSheetType = MutableStateFlow<BottomSheetType?>(null)
    val pokitOptionBottomSheetType = _pokitOptionBottomSheetType.asStateFlow()

    private val _currentShowingLink = MutableStateFlow<Link?>(null)
    val currentShowingLink = _currentShowingLink.asStateFlow()

    init {
        initLinkUpdateEventDetector()
        initLinkRemoveEventDetector()
        initLinkAddEventDetector()
        loadContents()
    }

    private fun initLinkUpdateEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.updatedLink.collectLatest { updatedLink ->
                unReadContents.value
                    .find { it.id == updatedLink.id }
                    ?.let { targetLink ->
                        val modifiedLink = targetLink
                            .copy(
                                title = updatedLink.title,
                                domain = updatedLink.domain,
                                createdAt = updatedLink.createdAt,
                                thumbNail = updatedLink.thumbnail
                            )
                        _unReadContents.update { contents ->
                            contents.map { content ->
                                if (content.id == targetLink.id) {
                                    modifiedLink
                                } else {
                                    content
                                }
                            }
                        }
                    }

                todayContents.value
                    .find { it.id == updatedLink.id }
                    ?.let { targetLink ->
                        val modifiedLink = targetLink
                            .copy(
                                title = updatedLink.title,
                                domain = updatedLink.domain,
                                createdAt = updatedLink.createdAt,
                                thumbNail = updatedLink.thumbnail
                            )
                        _todayContents.update { contents ->
                            contents.map { content ->
                                if (content.id == targetLink.id) {
                                    modifiedLink
                                } else {
                                    content
                                }
                            }
                        }
                    }

                bookmarkContents.value
                    .find { it.id == updatedLink.id }
                    ?.let { targetLink ->
                        val modifiedLink = targetLink
                            .copy(
                                title = updatedLink.title,
                                domain = updatedLink.domain,
                                createdAt = updatedLink.createdAt,
                                thumbNail = updatedLink.thumbnail
                            )
                        _bookmarkContents.update { contents ->
                            contents.map { content ->
                                if (content.id == targetLink.id) {
                                    modifiedLink
                                } else {
                                    content
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun initLinkRemoveEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.removedLink.collectLatest { removedLinkId ->
                unReadContents.value.filter { it.id != removedLinkId }

                todayContents.value.filter { it.id != removedLinkId }

                bookmarkContents.value.filter { it.id != removedLinkId }
            }
        }
    }

    private fun initLinkAddEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.addedLink.collectLatest {
                loadContents()
            }
        }
    }

    fun loadContents() {
        loadUnReadContents()
        loadTodayContents()
        loadMarkContents()
    }

    private fun loadUnReadContents() {
        viewModelScope.launch {
            _unReadContentNetworkState.update { NetworkState.LOADING }
            when (val response = unReadContentsUseCase.getUnreadContents()) {
                is PokitResult.Success -> {
                    _unReadContentNetworkState.update { NetworkState.IDLE }
                    _unReadContents.value = response.result.take(3)
                }
                is PokitResult.Error -> {
                    _unReadContentNetworkState.update { NetworkState.ERROR }
                }
            }
        }
    }

    private fun loadTodayContents() {
        viewModelScope.launch {
            _todayContentsNetworkState.update { NetworkState.LOADING }
            when (val response = todayContentsUseCase.getTodayContents()) {
                is PokitResult.Success -> {
                    _todayContentsNetworkState.update { NetworkState.IDLE }
                    _todayContents.value = response.result.take(3)
                }
                is PokitResult.Error -> {
                    _todayContentsNetworkState.update { NetworkState.ERROR }
                }
            }
        }
    }

    private fun loadMarkContents() {
        viewModelScope.launch {
            _bookmarkContentsNetworkState.update { NetworkState.LOADING }
            when (val response = bookMarkContentsUseCase.getBookmarkContents()) {
                is PokitResult.Success -> {
                    _bookmarkContentsNetworkState.update { NetworkState.IDLE }
                    _bookmarkContents.value = response.result.take(3)
                }
                is PokitResult.Error -> {
                    _bookmarkContentsNetworkState.update { NetworkState.ERROR }
                }
            }
        }
    }

    fun showDetailLinkBottomSheet(remindResult: RemindResult) {
        _currentShowingLink.update {
            Link(
                id = remindResult.id.toString(),
                title = remindResult.title,
                dateString = remindResult.createdAt,
                url = remindResult.data,
                isRead = remindResult.isRead,
                domainUrl = remindResult.domain
            )
        }
    }

    fun hideDetailLinkBottomSheet() {
        _currentShowingLink.update { null }
    }

    fun showLinkOptionBottomSheet(remindResult: RemindResult) {
        _currentSelectedLink.update {
            Link(
                id = remindResult.id.toString(),
                title = remindResult.title,
                dateString = remindResult.createdAt,
                url = remindResult.data,
                isRead = remindResult.isRead,
                domainUrl = remindResult.domain
            )
        }
        _pokitOptionBottomSheetType.update {
            BottomSheetType.MODIFY
        }
    }

    fun showLinkRemoveBottomSheet() {
        _pokitOptionBottomSheetType.update {
            BottomSheetType.REMOVE
        }
    }

    fun hideLinkOptionBottomSheet() {
        _currentSelectedLink.update { null }
        _pokitOptionBottomSheetType.update { null }
    }

    fun removeCurrentSelectedLink() {
        val currentSelectedLinkId = currentSelectedLink.value?.id?.toIntOrNull() ?: return
        viewModelScope.launch {
            val response = deleteLinkUseCase.deleteLink(currentSelectedLinkId)
            if (response is PokitResult.Success) {
                LinkUpdateEvent.removeSuccess(response.result)
            }
        }
    }
}
