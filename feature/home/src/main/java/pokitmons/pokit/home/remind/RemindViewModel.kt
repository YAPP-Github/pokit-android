package pokitmons.pokit.home.remind

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
