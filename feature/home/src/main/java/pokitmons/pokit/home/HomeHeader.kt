package pokitmons.pokit.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun HomeHeader() {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(id = R.drawable.icon_logo),
            tint = PokitTheme.colors.brand,
            contentDescription = "로고"
        )

        // Center: Auto space
        Spacer(modifier = Modifier.weight(1f))


        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.icon_24_search),
                contentDescription = "검색",
                modifier = Modifier.size(24.dp)
            )
            Icon(
                painterResource(id = R.drawable.icon_24_bell),
                contentDescription = "알림",
                modifier = Modifier.size(24.dp)
            )
            Icon(
                painterResource(id = R.drawable.icon_24_setup),
                contentDescription = "설정",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeHeader() {
    HomeHeader()
}

