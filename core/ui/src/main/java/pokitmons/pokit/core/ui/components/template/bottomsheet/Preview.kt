package pokitmons.pokit.core.ui.components.template.bottomsheet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.template.bottomsheet.subcomponents.PokitBottomSheetItem
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun PokitSwitchRadioPreview() {
    var showBottomSheet by remember { mutableStateOf(false) }

    PokitTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Button(onClick = { showBottomSheet = true }) {
                    Text(text = "Click!")
                }
            }

            if (showBottomSheet) {
                PokitBottomSheet(
                    onHideBottomSheet = { showBottomSheet = false }
                ) {
                    PokitBottomSheetItem(
                        text = "즐겨찾기",
                        resourceId = R.drawable.icon_24_star_1,
                        data = "즐겨찾기",
                        onClick = { }
                    )

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = PokitTheme.colors.borderTertiary
                    )

                    PokitBottomSheetItem(
                        text = "공유하기",
                        resourceId = R.drawable.icon_24_share,
                        data = "공유하기",
                        onClick = { showBottomSheet = false }
                    )
                }
            }
        }
    }
}
