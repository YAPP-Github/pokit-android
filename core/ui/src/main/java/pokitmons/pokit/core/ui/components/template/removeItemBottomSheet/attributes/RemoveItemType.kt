package pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.attributes

import pokitmons.pokit.core.ui.R

enum class RemoveItemType(val titleStringResourceId: Int, val subStringResourceId: Int) {
    POKIT(
        titleStringResourceId = R.string.title_remove_pokit,
        subStringResourceId = R.string.sub_remove_link
    ),
    LINK(
        titleStringResourceId = R.string.title_remove_link,
        subStringResourceId = R.string.sub_remove_link
    ),
}
