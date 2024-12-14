package pokitmons.pokit.uncategorized

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.uncategorized.model.Pokit
import pokitmons.pokit.uncategorized.model.UncategorizedLink
import pokitmons.pokit.uncategorized.model.UncategorizedScreenState

@Preview(showBackground = true)
@Composable
internal fun Preview() {
    PokitTheme {
        Column {
            UncategorizedScreen(
                viewModel = dummyViewModel,
                onBackPressed = {}
            )
        }
    }
}

private val dummyViewModel = object : UncategorizedViewModel {
    override fun toggleLinkSelected(linkId: String) {
    }

    override fun toggleAllLinksSelected() {
    }

    override fun removeSelectedLinks() {
    }

    override fun showPokitSelectBottomSheet() {
    }

    override fun hidePokitSelectBottomSheet() {
    }

    override fun moveSelectedLinks(pokitId: String) {
    }

    override fun loadNextLinks() {
    }

    override fun refreshPokits() {
    }

    override fun loadNextPokits() {
    }

    override val state: StateFlow<UncategorizedScreenState>
        get() = MutableStateFlow(UncategorizedScreenState(selectAll = false, updateLoading = false, showPokitSelectBottomSheet = false))
    override val linkList: StateFlow<List<UncategorizedLink>>
        get() = MutableStateFlow(emptyList())
    override val linkListState: StateFlow<PagingState>
        get() = MutableStateFlow(PagingState.IDLE)
    override val pokitList: StateFlow<List<Pokit>>
        get() = MutableStateFlow(emptyList())
    override val pokitListState: StateFlow<PagingState>
        get() = MutableStateFlow(PagingState.IDLE)
    override val linkChanged: Boolean
        get() = false
}
