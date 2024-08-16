package pokitmons.pokit.home.remind

import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.take
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard

@Composable
fun RemindScreen(
    modifier: Modifier = Modifier,
    viewModel: RemindViewModel = hiltViewModel(),
) {

    viewModel.loadContents()

    val unreadContents = viewModel.unReadContents.collectAsState()
    val todayContents = viewModel.todayContents.collectAsState()
    val bookmarkContents = viewModel.bookmarkContents.collectAsState()

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        RemindSection(title = "오늘 이 링크는 어때요?") {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                todayContents.value.forEach { todayContent ->
                    ToadyLinkCard(
                        title = todayContent.title,
                        sub = todayContent.createdAt,
                        painter = rememberAsyncImagePainter(todayContent.thumbNail),
                        badgeText = todayContent.data,
                        domain = todayContent.domain
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        RemindSection(title = "한번도 읽지 않았어요") {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                unreadContents.value.forEach { unReadContent ->
                    LinkCard(
                        item = unReadContent.title,
                        title = unReadContent.title,
                        sub = "${unReadContent.createdAt} • ${unReadContent.domain}",
                        painter = rememberAsyncImagePainter(unReadContent.thumbNail),
                        notRead = unReadContent.isRead,
                        badgeText = unReadContent.data,
                        onClickKebab = { },
                        onClickItem = { }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        RemindSection(title = "즐겨찾기 링크만 모았어요") {
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                bookmarkContents.value.forEach { unReadContent ->
                    LinkCard(
                        item = unReadContent.title,
                        title = unReadContent.title,
                        sub = "${unReadContent.createdAt} • ${unReadContent.domain}",
                        painter = rememberAsyncImagePainter(unReadContent.thumbNail),
                        notRead = unReadContent.isRead,
                        badgeText = unReadContent.data,
                        onClickKebab = { },
                        onClickItem = { }
                    )
                }
            }
        }
    }
}
