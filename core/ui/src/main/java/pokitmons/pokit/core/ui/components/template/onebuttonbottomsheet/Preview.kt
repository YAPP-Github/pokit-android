package pokitmons.pokit.core.ui.components.template.onebuttonbottomsheet

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
private fun Preview() {
    PokitTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OneButtonBottomSheetContent(
                    title = "로그인 오류",
                    sub = "현재 서버 오류로 로그인에 실패했습니다.\n잠시 후에 다시 시도해 주세요."
                )

                HorizontalDivider(
                    modifier = Modifier.height(16.dp).background(PokitTheme.colors.borderTertiary)
                )

                OneButtonBottomSheetContent(
                    title = "여기에 타이틀이 들어갑니다"
                )
            }
        }
    }
}
