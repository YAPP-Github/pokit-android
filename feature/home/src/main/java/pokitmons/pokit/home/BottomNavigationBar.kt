package pokitmons.pokit.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun BottomNavigationBar() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        shadowElevation = 20.dp
    ) {
        BottomAppBar(
            containerColor = Color.White,
            contentColor = Color.Black,
            modifier = Modifier.height(92.dp),
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(bottom = 24.dp)
                    .clickable { /* 리마인드 버튼 클릭 동작 */ },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_folder),
                    contentDescription = "리마인드",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    style = PokitTheme.typography.detail2,
                    text = "포킷",
                    textAlign = TextAlign.Center
                )
            }

            // 가운데 공간 (플로팅 액션 버튼 자리)
            Box(modifier = Modifier.weight(1f)) {

            }

            // 리마인드 버튼
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(bottom = 24.dp)
                    .clickable { /* 리마인드 버튼 클릭 동작 */ },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "리마인드",
                    tint = Color.LightGray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    style = PokitTheme.typography.detail2,
                    text = "리마인드",
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}


@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}
