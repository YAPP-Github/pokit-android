package pokitmons.pokit.home.remind

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.home.HomeViewModel
import pokitmons.pokit.home.pokit.LinkCardDummy

@Composable
fun RemindScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val dummy = arrayListOf<LinkCardDummy>().apply {
        add(LinkCardDummy())
        add(LinkCardDummy())
        add(LinkCardDummy())
    }.toList()


    Column(
        modifier = modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        RemindSection(title = "오늘 이 링크는 어때요?") {
            Spacer(modifier = Modifier.height(12.dp))
            ToadyLinkCard()
        }

        Spacer(modifier = Modifier.height(32.dp))

        RemindSection(title = "한번도 읽지 않았어요") {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                dummy.forEach { linkCardInfo ->
                    LinkCard(
                        item = linkCardInfo.item,
                        title = linkCardInfo.title,
                        sub = linkCardInfo.sub,
                        painter = painterResource(id = R.drawable.icon_24_folder),
                        notRead = linkCardInfo.notRead,
                        badgeText = linkCardInfo.badgeText,
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
                dummy.forEach { linkCardInfo ->
                    LinkCard(
                        item = linkCardInfo.item,
                        title = linkCardInfo.title,
                        sub = linkCardInfo.sub,
                        painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_folder),
                        notRead = linkCardInfo.notRead,
                        badgeText = linkCardInfo.badgeText,
                        onClickKebab = { },
                        onClickItem = { }
                    )
                }
            }
        }
    }
}
