package pokitmons.pokit.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.home.pokit.PokitScreen
import pokitmons.pokit.home.pokit.PokitViewModel
import pokitmons.pokit.home.pokit.ScreenType
import pokitmons.pokit.home.remind.RemindScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// TODO 화면 단으로 분라
fun HomeScreen(viewModel: PokitViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = PokitTheme.colors.brand,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .size(96.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            tint = PokitTheme.colors.inverseWh,
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.icon_24_link),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            style = PokitTheme.typography.body3Medium,
                            text = "링크추가",
                            color = PokitTheme.colors.inverseWh
                        )
                    }

                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))

                    Column(
                        modifier = Modifier
                            .background(
                                color = PokitTheme.colors.brand,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .size(96.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            tint = PokitTheme.colors.inverseWh,
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.icon_24_folder),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            style = PokitTheme.typography.body3Medium,
                            text = "포킷추가",
                            color = PokitTheme.colors.inverseWh
                        )
                    }
                }
            }
        }

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
                .clickable {
                    showBottomSheet = true
                }
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
