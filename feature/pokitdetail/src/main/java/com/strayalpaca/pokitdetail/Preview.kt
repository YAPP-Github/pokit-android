package com.strayalpaca.pokitdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.strayalpaca.pokitdetail.model.Filter
import com.strayalpaca.pokitdetail.model.Link
import com.strayalpaca.pokitdetail.model.Pokit
import com.strayalpaca.pokitdetail.model.PokitDetailScreenState
import com.strayalpaca.pokitdetail.model.sampleLinkList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PokitDetailScreen(
                viewModel = dummyViewModel
            )
        }
    }
}

val dummyViewModel = object : PokitDetailViewModel {
    override val pokitList: StateFlow<List<Pokit>>
        get() = MutableStateFlow(emptyList())
    override val pokitListState: StateFlow<PagingState>
        get() = MutableStateFlow(PagingState.IDLE)
    override val linkList: StateFlow<List<Link>>
        get() = MutableStateFlow(sampleLinkList)
    override val linkListState: StateFlow<PagingState>
        get() = MutableStateFlow(PagingState.IDLE)
    override val moveToBackEvent: EventFlow<Boolean>
        get() = MutableEventFlow()
    override val state: StateFlow<PokitDetailScreenState>
        get() = MutableStateFlow(PokitDetailScreenState())

    override fun changePokit(pokit: Pokit) {}

    override fun changeFilter(filter: Filter) {}

    override fun showPokitModifyBottomSheet() {}

    override fun showPokitRemoveBottomSheet() {}

    override fun hidePokitBottomSheet() {}

    override fun showLinkRemoveBottomSheet() {}

    override fun showLinkRemoveBottomSheet(link: Link) {}

    override fun hideLinkBottomSheet() {}

    override fun showLinkDetailBottomSheet(link: Link) {}

    override fun hideLinkDetailBottomSheet() {}

    override fun showFilterChangeBottomSheet() {}

    override fun hideFilterChangeBottomSheet() {}

    override fun showPokitSelectBottomSheet() {}

    override fun hidePokitSelectBottomSheet() {}

    override fun loadNextPokits() {}

    override fun refreshPokits() {}

    override fun loadNextLinks() {}

    override fun deletePokit(pokit: Pokit) {}

    override fun deleteLink(link: Link) {}

    override fun toggleBookmark(link: Link) {}
}
