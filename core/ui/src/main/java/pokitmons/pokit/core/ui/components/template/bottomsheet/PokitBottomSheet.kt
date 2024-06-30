package pokitmons.pokit.core.ui.components.template.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokitBottomSheet(
    onHideBottomSheet: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit),
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onHideBottomSheet,
        sheetState = bottomSheetState,
        scrimColor = Color.Transparent,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = PokitTheme.colors.backgroundBase,
        dragHandle = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .width(36.dp)
                        .height(4.dp)
                        .background(color = PokitTheme.colors.iconTertiary)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    ) {
        content()
    }
}
