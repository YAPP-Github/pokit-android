package pokitmons.pokit.linklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.linklist.model.Link
import pokitmons.pokit.linklist.model.LinkListScreenState

@Preview(showBackground = true)
@Composable
fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LinkListScreen(
                viewModel = dummyLinkListViewModel,
                onBackPressed = { },
                onClickModifyLink = {}
            )
        }
    }
}

private val dummyLinkListScreenState = LinkListScreenState()

private val dummyLinkListViewModel = object : LinkListViewModel {
    override val state: StateFlow<LinkListScreenState>
        get() = MutableStateFlow(dummyLinkListScreenState)
    override val linkList: StateFlow<List<Link>>
        get() = MutableStateFlow(emptyList())
    override val linkListState: StateFlow<PagingState>
        get() = MutableStateFlow(PagingState.IDLE)

    override fun loadNextLinks() {}

    override fun refreshLinks() {}

    override fun toggleSortType() {}

    override fun toggleBookmark(link: Link) {}

    override fun showLinkDetailBottomSheet(link: Link) {}

    override fun hideLinkDetailBottomSheet() {}

    override fun showCheckLinkRemoveBottomSheet() {}

    override fun hideCheckLinkRemoveBottomSheet() {}

    override fun removeLink(linkId: String) {}
}
