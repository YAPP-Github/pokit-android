package pokitmons.pokit.core.ui.components.template.removeItemBottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun RemoveItemBottomSheetContentPreview() {
    PokitTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                TwoButtonBottomSheetContent(
                    title = "포킷을 정말 삭제하시겠습니까?",
                    subText = "함께 저장한 모든 링크가 삭제되며,\n복구하실 수 없습니다.",
                    onClickLeftButton = {},
                    onClickRightButton = {}
                )

                HorizontalDivider(
                    modifier = Modifier.height(16.dp).background(PokitTheme.colors.borderTertiary)
                )

                TwoButtonBottomSheetContent(
                    title = "로그아웃 하시겠습니까?",
                    onClickLeftButton = {},
                    onClickRightButton = {}
                )
            }
        }
    }
}
