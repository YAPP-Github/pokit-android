package pokitmons.pokit.core.ui.components.template.removeItemBottomSheet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.attributes.RemoveItemType
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun RemoveItemBottomSheetPreview() {
    var showBottomSheet by remember { mutableStateOf(false) }

    PokitTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Button(onClick = { showBottomSheet = true }) {
                Text(text = "Click!")
            }

            if (showBottomSheet) {
                RemoveItemBottomSheet(
                    removeItemType = RemoveItemType.LINK,
                    onHideBottomSheet = remember {
                        { showBottomSheet = false }
                    },
                    onClickCancel = {},
                    onClickRemove = {}
                )
            }
        }
    }
}
