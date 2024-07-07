package pokitmons.pokit.core.ui.components.block.pushcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun PushCardPreview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PushCard(
                title = "일단 글 링크의 타이틀은 조금 길수도 있으니까 아무렇게나 길게 적어봅니다",
                sub = "내일 알림이 예정되어 있어요!\n잊지 말고 링크를 확인하세요!",
                timeString = "1시간 전",
                painter = null,
                onClick = {},
                read = false
            )

            PushCard(
                title = "일단 글 링크의 타이틀은 조금 길수도 있으니까 아무렇게나 길게 적어봅니다",
                sub = "내일 알림이 예정되어 있어요!\n잊지 말고 링크를 확인하세요!",
                timeString = "1시간 전",
                painter = null,
                onClick = {},
                read = true
            )

            PushCard(
                title = "일단 글 링크의 타이틀은 조금 길수도 있으니까 아무렇게나 길게 적어봅니다",
                sub = "내일 알림이 예정되어 있어요!\n잊지 말고 링크를 확인하세요!",
                timeString = "1시간 전",
                painter = painterResource(id = R.drawable.icon_24_google),
                onClick = {},
                read = false
            )
            PushCard(
                title = "일단 글 링크의 타이틀은 조금 길수도 있으니까 아무렇게나 길게 적어봅니다",
                sub = "내일 알림이 예정되어 있어요!\n잊지 말고 링크를 확인하세요!",
                timeString = "1시간 전",
                painter = painterResource(id = R.drawable.icon_24_google),
                onClick = {},
                read = true
            )
        }
    }
}
