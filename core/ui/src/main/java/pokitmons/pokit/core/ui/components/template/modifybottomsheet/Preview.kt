package pokitmons.pokit.core.ui.components.template.modifybottomsheet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun ModifyBottomSheetContentPreview() {
    PokitTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ModifyBottomSheetContent(
                onClickRemove = {},
                onClickModify = {},
                onClickShare = {}
            )
        }
    }
}
