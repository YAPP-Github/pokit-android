package pokitmons.pokit.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.home.pokit.PokitScreen
import pokitmons.pokit.home.pokit.PokitViewModel
import pokitmons.pokit.home.pokit.ScreenType
import pokitmons.pokit.home.remind.RemindScreen

@Composable
fun HomeScreen(viewModel: PokitViewModel = hiltViewModel()) {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
        ) {
            HomeHeader()
            Scaffold(
                bottomBar = { BottomNavigationBar() },
            ) { padding ->
                when (viewModel.screenType.value) {
                    is ScreenType.Pokit -> PokitScreen(Modifier.padding(padding))
                    is ScreenType.Remind -> RemindScreen(Modifier.padding(padding))
                }
            }
        }
        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp),
            painter = painterResource(id = R.drawable.image_floating),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview(viewModel: PokitViewModel = hiltViewModel()) {
    HomeScreen()
}
