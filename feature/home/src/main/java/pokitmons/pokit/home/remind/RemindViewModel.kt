package pokitmons.pokit.home.remind

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.home.remind.RemindResult
import pokitmons.pokit.domain.usecase.home.remind.BookMarkContentsUseCase
import pokitmons.pokit.domain.usecase.home.remind.TodayContentsUseCase
import pokitmons.pokit.domain.usecase.home.remind.UnReadContentsUseCase
import javax.inject.Inject

@HiltViewModel
class RemindViewModel @Inject constructor(
    private val unReadContentsUseCase: UnReadContentsUseCase,
    private val todayContentsUseCase: TodayContentsUseCase,
    private val bookMarkContentsUseCase: BookMarkContentsUseCase,
) : ViewModel() {

    init {
        loadContents()
        initLinkUpdateEventDetector()
    }

    private var _unReadContents: MutableStateFlow<List<RemindResult>> = MutableStateFlow(emptyList())
    val unReadContents: StateFlow<List<RemindResult>>
        get() = _unReadContents.asStateFlow()

    private var _todayContents: MutableStateFlow<List<RemindResult>> = MutableStateFlow(emptyList())
    val todayContents: StateFlow<List<RemindResult>>
        get() = _todayContents.asStateFlow()

    private var _bookmarkContents: MutableStateFlow<List<RemindResult>> = MutableStateFlow(emptyList())
    val bookmarkContents: StateFlow<List<RemindResult>>
        get() = _bookmarkContents.asStateFlow()

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
                                if (content.id == targetLink.id) modifiedLink
                                else content
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
                                if (content.id == targetLink.id) modifiedLink
                                else content
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
                                if (content.id == targetLink.id) modifiedLink
                                else content
                            }
                        }
                    }
            }
        }
    }

    fun loadContents() {
        viewModelScope.launch {
            when (val response = unReadContentsUseCase.getUnreadContents()) {
                is PokitResult.Success -> _unReadContents.value = response.result.take(3)
                is PokitResult.Error -> {}
            }

            when (val response = todayContentsUseCase.getTodayContents()) {
                is PokitResult.Success -> {
                    _todayContents.value = response.result
                }
                is PokitResult.Error -> {
                }
            }

            when (val response = bookMarkContentsUseCase.getBookmarkContents()) {
                is PokitResult.Success -> _bookmarkContents.value = response.result.take(3)
                is PokitResult.Error -> {}
            }
        }
    }
}
