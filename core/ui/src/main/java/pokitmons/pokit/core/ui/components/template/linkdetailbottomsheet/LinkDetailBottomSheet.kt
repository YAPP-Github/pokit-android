package pokitmons.pokit.core.ui.components.template.linkdetailbottomsheet

import androidx.compose.runtime.Composable
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet

@Composable
fun LinkDetailBottomSheet(
    title: String,
    memo: String,
    bookmark: Boolean,
    pokitName: String,
    dateString: String,
    onHideBottomSheet: () -> Unit,
    show: Boolean = false,
    onClickBookmark: (() -> Unit)? = null,
    onClickRemoveLink: (() -> Unit)? = null,
    onClickModifyLink: (() -> Unit)? = null,
    onClickShareLink: (() -> Unit)? = null,
) {
    PokitBottomSheet(
        onHideBottomSheet = onHideBottomSheet,
        show = show
    ) {
        LinkDetailBottomSheetContent(
            title = title,
            memo = memo,
            bookmark = bookmark,
            pokitName = pokitName,
            dateString = dateString,
            onClickBookmark = onClickBookmark,
            onClickRemoveLink = onClickRemoveLink,
            onClickModifyLink = onClickModifyLink,
            onClickShareLink = onClickShareLink
        )
    }
}
