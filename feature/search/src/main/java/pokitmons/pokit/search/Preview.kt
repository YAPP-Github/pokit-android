package pokitmons.pokit.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.model.Link
import pokitmons.pokit.search.model.LinkType

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchScreen(
                linkList = sampleLinks
            )
        }
    }
}

internal val sampleLinks = listOf(
    Link(
        id = "1",
        title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
        imageUrl = null,
        dateString = "2024.04.12",
        domainUrl = "youtu.be",
        isRead = true,
        linkType = LinkType.TEXT,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "2",
        title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
        imageUrl = null,
        dateString = "2024.05.12",
        domainUrl = "youtu.be",
        isRead = false,
        linkType = LinkType.TEXT,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "3",
        title = "포킷포킷",
        imageUrl = null,
        dateString = "2024.04.12",
        domainUrl = "pokitmons.pokit",
        isRead = true,
        linkType = LinkType.TEXT,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "4",
        title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
        imageUrl = null,
        dateString = "2024.06.12",
        domainUrl = "youtu.be",
        isRead = true,
        linkType = LinkType.TEXT,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "5",
        title = "마지막 링크입니다.",
        imageUrl = null,
        dateString = "2024.07.14",
        domainUrl = "youtu.be",
        isRead = false,
        linkType = LinkType.TEXT,
        url = "",
        memo = "",
        bookmark = true
    )
)
