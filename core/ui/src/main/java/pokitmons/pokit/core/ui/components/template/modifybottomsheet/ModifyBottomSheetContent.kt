package pokitmons.pokit.core.ui.components.template.modifybottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.block.texticonbutton.TextIconButton

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
            TextIconButton(
                onClick = onClickShare,
                title = stringResource(id = R.string.share),
                painter = painterResource(id = R.drawable.icon_24_share)
            )
        }

        onClickModify?.let { onClickModify ->
            TextIconButton(
                onClick = onClickModify,
                title = stringResource(id = R.string.modify),
                painter = painterResource(id = R.drawable.icon_24_edit)
            )
        }

        onClickRemove?.let { onClickRemove ->
            TextIconButton(
                onClick = onClickRemove,
                title = stringResource(id = R.string.remove),
                painter = painterResource(id = R.drawable.icon_24_trash)
            )
        }
    }
}
