package pokitmons.pokit.search.components.searchitemlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.model.Link
import pokitmons.pokit.search.paging.SimplePagingState
import pokitmons.pokit.core.ui.R.drawable as coreDrawable
import pokitmons.pokit.search.R.string as SearchString

@Composable
internal fun SearchItemList(
    modifier: Modifier = Modifier,
    onToggleSort: () -> Unit = {},
    useRecentOrder: Boolean = true,
    links: List<Link> = emptyList(),
    linkPagingState: SimplePagingState = SimplePagingState.IDLE,
    onClickLinkKebab: (Link) -> Unit = {},
    onClickLink: (Link) -> Unit = {},
    loadNextLinks: () -> Unit = {},
) {
    val linkLazyColumnListState = rememberLazyListState()
    val startLinkPaging = remember {
        derivedStateOf {
            linkLazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let { last ->
                last.index >= linkLazyColumnListState.layoutInfo.totalItemsCount - 3
            } ?: false
        }
    }

    LaunchedEffect(startLinkPaging.value) {
        if (startLinkPaging.value && linkPagingState == SimplePagingState.IDLE) {
            loadNextLinks()
        }
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 16.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onToggleSort
                )
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = coreDrawable.icon_24_align),
                contentDescription = "change sort order",
                colorFilter = ColorFilter.tint(color = PokitTheme.colors.iconPrimary)
            )

            Text(
                text = stringResource(id = if (useRecentOrder) SearchString.sort_order_recent else SearchString.sort_order_older),
                style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.textSecondary)
            )
        }

        LazyColumn(
            state = linkLazyColumnListState
        ) {
            items(links) { link ->
                LinkCard(
                    item = link,
                    title = link.title,
                    sub = "${link.dateString} · ${link.domainUrl}",
                    painter = painterResource(id = coreDrawable.icon_24_google),
                    notRead = link.isRead,
                    badgeText = stringResource(id = link.linkType.textResourceId),
                    onClickKebab = onClickLinkKebab,
                    onClickItem = onClickLink,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }
}
