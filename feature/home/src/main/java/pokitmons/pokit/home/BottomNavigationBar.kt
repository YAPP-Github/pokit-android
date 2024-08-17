package pokitmons.pokit.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.home.pokit.PokitViewModel
import pokitmons.pokit.home.pokit.ScreenType
import pokitmons.pokit.core.ui.R.drawable as DrawableResource

// TODO : 바텀시트 아이템 컴포저블로 만들기

@Composable
fun BottomNavigationBar(viewModel: PokitViewModel = hiltViewModel()) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        shadowElevation = 20.dp
    ) {
        BottomAppBar(
            containerColor = PokitTheme.colors.backgroundBase,
            modifier = Modifier.height(92.dp),
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(bottom = 24.dp)
                    .clickable { viewModel.updateScreenType(ScreenType.Pokit) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = DrawableResource.icon_24_folder),
                    contentDescription = "리마인드",
                    tint = when (viewModel.screenType.value) {
                        is ScreenType.Pokit -> Color.Black
                        is ScreenType.Remind -> PokitTheme.colors.iconTertiary
                    },
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    color = when (viewModel.screenType.value) {
                        is ScreenType.Pokit -> Color.Black
                        is ScreenType.Remind -> PokitTheme.colors.textTertiary
                    },
                    style = PokitTheme.typography.detail2,
                    text = "포킷",
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(bottom = 24.dp)
                    .clickable { viewModel.updateScreenType(ScreenType.Remind) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = DrawableResource.icon_24_remind),
                    contentDescription = "리마인드",
                    tint = when (viewModel.screenType.value) {
                        is ScreenType.Remind -> Color.Black
                        is ScreenType.Pokit -> PokitTheme.colors.iconTertiary
                    },
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    color = when (viewModel.screenType.value) {
                        is ScreenType.Remind -> Color.Black
                        is ScreenType.Pokit -> PokitTheme.colors.textTertiary
                    },
                    style = PokitTheme.typography.detail2,
                    text = "리마인드",
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
