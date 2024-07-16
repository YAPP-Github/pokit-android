package pokitmons.pokit.core.ui.components.template.modifybottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.template.modifybottomsheet.subcomponents.ModifyBottomSheetItem

@Composable
fun ModifyBottomSheetContent(
    onClickShare: (() -> Unit)? = null,
    onClickModify: (() -> Unit)? = null,
    onClickRemove: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        onClickShare?.let { onClickShare ->
            ModifyBottomSheetItem(
                onClick = onClickShare,
                title = stringResource(id = R.string.share),
                painter = painterResource(id = R.drawable.icon_24_share)
            )
        }

        onClickModify?.let { onClickModify ->
            ModifyBottomSheetItem(
                onClick = onClickModify,
                title = stringResource(id = R.string.modify),
                painter = painterResource(id = R.drawable.icon_24_edit)
            )
        }

        onClickRemove?.let { onClickRemove ->
            ModifyBottomSheetItem(
                onClick = onClickRemove,
                title = stringResource(id = R.string.remove),
                painter = painterResource(id = R.drawable.icon_24_trash)
            )
        }
    }
}
