package pokitmons.pokit.core.ui.components.template.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsIgnoringVisibility
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pokitmons.pokit.core.ui.theme.PokitTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PokitBottomSheet(
    onHideBottomSheet: () -> Unit,
    show: Boolean = false,
    content: @Composable (ColumnScope.() -> Unit),
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var visibility by remember { mutableStateOf(show) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(show) {
        if (visibility && !show) {
            scope.launch {
                bottomSheetState.hide()
            }.invokeOnCompletion {
                onHideBottomSheet()
                visibility = false
            }
        } else {
            visibility = show
        }
    }

    if (visibility) {
        ModalBottomSheet(
            onDismissRequest = remember {
                {
                    onHideBottomSheet()
                    visibility = false
                }
            },
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

            Spacer(
                Modifier.windowInsetsBottomHeight(
                    WindowInsets.navigationBarsIgnoringVisibility
                )
            )
        }
    }

}
