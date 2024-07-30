package pokitmons.pokit.search.components.filterbottomsheet

import androidx.compose.runtime.Composable
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.FilterType
import pokitmons.pokit.search.model.Pokit
import pokitmons.pokit.search.model.samplePokits

@Composable
fun FilterBottomSheet(
    filter: Filter = Filter(),
    firstShowType: FilterType = FilterType.Pokit,
    onSaveClilck: (Filter) -> Unit = {},
    pokits: List<Pokit> = samplePokits,
    show: Boolean = false,
    onDismissRequest: () -> Unit = {},
) {
    PokitBottomSheet(
        onHideBottomSheet = onDismissRequest,
        show = show
    ) {
        FilterBottomSheetContent(
            filter = filter,
            firstShowType = firstShowType,
            onSaveClilck = onSaveClilck,
            pokits = pokits
        )
    }
}
