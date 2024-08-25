package pokitmons.pokit.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.flow.collectAsEffect
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.block.pokittoast.PokitToast
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.home.model.HomeSideEffect
import pokitmons.pokit.core.ui.utils.noRippleClickable
import pokitmons.pokit.home.pokit.PokitScreen
import pokitmons.pokit.home.pokit.PokitViewModel
import pokitmons.pokit.home.pokit.ScreenType
import pokitmons.pokit.home.remind.RemindScreen

// TODO 화면 단으로 분라
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PokitViewModel,
    onNavigateToPokitDetail: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToSetting: () -> Unit,
    onNavigateAddLink: () -> Unit,
    onNavigateAddPokit: () -> Unit,
    onNavigateToLinkModify: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val toastMessage by viewModel.toastMessage.collectAsState()

    viewModel.sideEffect.collectAsEffect { homeSideEffect: HomeSideEffect ->
        when(homeSideEffect) {
            HomeSideEffect.NavigateToAddPokit -> {
                onNavigateAddPokit()
            }
        }
    }

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
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = PokitTheme.colors.brand,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .size(96.dp)
                            .noRippleClickable {
                                scope.launch {
                                    sheetState.hide()
                                    showBottomSheet = false
                                    onNavigateAddLink()
                                }
                            },
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
                            .size(96.dp)
                            .noRippleClickable {
                                scope.launch {
                                    sheetState.hide()
                                    showBottomSheet = false
                                    viewModel.checkPokitCount()
                                }
                            },
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

        // screen
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
        ) {
            HomeHeader(
                viewModel = viewModel,
                onNavigateToSearch = { onNavigateToSearch() },
                onNavigateToSetting = { onNavigateToSetting() }
            )
            Scaffold(
                bottomBar = { BottomNavigationBar() }
            ) { padding ->
                Box {
                    when (viewModel.screenType.value) {
                        is ScreenType.Pokit -> {
                            PokitScreen(
                                viewModel = viewModel,
                                modifier = Modifier.padding(padding),
                                onNavigateToPokitDetail = onNavigateToPokitDetail
                            )
                        }

                        is ScreenType.Remind -> {
                            RemindScreen(
                                modifier = Modifier.padding(padding),
                                onNavigateToLinkModify = onNavigateToLinkModify
                            )
                        }
                    }

                    toastMessage?.let { toastMessageEvent ->
                        PokitToast(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(padding)
                                .padding(start = 12.dp, end = 12.dp, bottom = 48.dp),
                            text = stringResource(id = toastMessageEvent.resourceId),
                            onClickClose = viewModel::closeToastMessage
                        )
                    }
                }
            }
        }
        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
                .noRippleClickable {
                    showBottomSheet = true
                },
            painter = painterResource(id = R.drawable.image_floating),
            contentDescription = null
        )
    }
}
