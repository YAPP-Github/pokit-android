package pokitmons.pokit.home

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable
import pokitmons.pokit.home.pokit.PokitViewModel
import pokitmons.pokit.home.pokit.ScreenType

@Composable
fun HomeHeader(
    viewModel: PokitViewModel,
    onNavigateToSetting: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToAlarm: () -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 20.dp, end = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = when (viewModel.screenType.value) {
                is ScreenType.Pokit -> painterResource(id = R.drawable.logo_pokit)
                is ScreenType.Remind -> painterResource(id = R.drawable.logo_remind)
            },
            tint = PokitTheme.colors.brand,
            contentDescription = "로고"
        )

        Spacer(modifier = Modifier.weight(1f))

        Row {
            Icon(
                painterResource(id = R.drawable.icon_24_search),
                contentDescription = "검색",
                modifier = Modifier
                    .size(36.dp)
                    .noRippleClickable { onNavigateToSearch() }
                    .padding(6.dp)
            )
            Icon(
                painterResource(id = R.drawable.icon_24_bell),
                contentDescription = "알림",
                modifier = Modifier
                    .size(36.dp)
                    .noRippleClickable { onNavigateToAlarm() }
                    .padding(6.dp)
            )

            when (viewModel.screenType.value) {
                is ScreenType.Pokit -> {
                    Icon(
                        painterResource(id = R.drawable.icon_24_setup),
                        contentDescription = "설정",
                        modifier = Modifier
                            .size(36.dp)
                            .noRippleClickable { onNavigateToSetting() }
                            .padding(6.dp)
                    )
                }
                is ScreenType.Remind -> Unit
            }
        }
    }
}
