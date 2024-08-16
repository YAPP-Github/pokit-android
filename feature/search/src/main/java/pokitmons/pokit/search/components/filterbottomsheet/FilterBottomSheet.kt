package pokitmons.pokit.search.components.filterbottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.FilterType
import pokitmons.pokit.search.model.Pokit
import pokitmons.pokit.search.model.samplePokits
import pokitmons.pokit.search.paging.SimplePagingState

@Composable
fun FilterBottomSheet(
    filter: Filter = Filter(),
    firstShowType: FilterType = FilterType.Pokit,
    onSaveClilck: (Filter) -> Unit = {},
    pokits: List<Pokit> = samplePokits,
    pokitPagingState: SimplePagingState = SimplePagingState.IDLE,
    loadNextPokits: () -> Unit = {},
    refreshPokits: () -> Unit = {},
    show: Boolean = false,
    onDismissRequest: () -> Unit = {},
) {
    PokitBottomSheet(
        onHideBottomSheet = onDismissRequest,
        show = show
    ) {
        LaunchedEffect(Unit) {
            refreshPokits()
        }

        FilterBottomSheetContent(
            filter = filter,
            firstShowType = firstShowType,
            onSaveClilck = onSaveClilck,
            pokits = pokits,
            pokitPagingState = pokitPagingState,
            loadNextPokits = loadNextPokits
        )
    }
}
