package pokitmons.pokit.core.ui.components.block.linkcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun LinkCardPreview() {
    PokitTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LinkCard(
                title = "타이틀\n컴포스는 왜 이런가",
                sub = "2024.06.25. youtube.com",
                badgeText = "텍스트",
                painter = painterResource(id = R.drawable.icon_24_link),
                notRead = true,
                item = 3,
                onClickKebab = { value: Int -> },
                onClickItem = { value: Int -> }
            )

            LinkCard(
                title = "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세",
                sub = "2024.06.25. youtube.com",
                badgeText = "텍스트",
                painter = painterResource(id = R.drawable.icon_24_link),
                notRead = true,
                item = 3,
                onClickKebab = { value: Int -> },
                onClickItem = { value: Int -> }
            )
        }
    }
}
