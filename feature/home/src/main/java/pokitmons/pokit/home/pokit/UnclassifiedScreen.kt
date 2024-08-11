package pokitmons.pokit.home.pokit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard

@Composable
fun UnclassifiedScreen() {
    val dummy = arrayListOf<LinkCardDummy>().apply {
        add(LinkCardDummy())
        add(LinkCardDummy())
        add(LinkCardDummy())
        add(LinkCardDummy())
        add(LinkCardDummy())
    }.toList()

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(items = dummy) { linkCardInfo ->
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

data class LinkCardDummy(
    val text: String = "요리/레시피",
    val linkCount: Int = 10,
    val item: String = "",
    val title: String = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
    val sub: String = "2024.04.12 • youtu.be",
    val painter: Int = pokitmons.pokit.core.ui.R.drawable.icon_24_folder,
    val notRead: Boolean = true,
    val badgeText: String = "미분류",
)

@Preview
@Composable
fun LinkCardPreview2() {
    UnclassifiedScreen()
}
