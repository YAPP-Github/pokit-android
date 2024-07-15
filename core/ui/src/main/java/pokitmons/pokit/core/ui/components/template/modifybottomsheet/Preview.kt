package pokitmons.pokit.core.ui.components.template.modifybottomsheet

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
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun ModifyBottomSheetPreview() {
    var showBottomSheet by remember { mutableStateOf(false) }

    PokitTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Button(onClick = { showBottomSheet = true }) {
                Text(text = "Click!")
            }

            if (showBottomSheet) {
                ModifyBottomSheet(
                    onHideBottomSheet = {},
                    onClickRemove = {},
                    onClickModify = {},
                    onClickShare = {}
                )
            }
        }
    }
}
