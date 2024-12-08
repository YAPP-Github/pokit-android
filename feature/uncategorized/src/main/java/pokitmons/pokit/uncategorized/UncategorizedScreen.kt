package pokitmons.pokit.uncategorized

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.atom.loading.LoadingProgress
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.core.ui.components.block.pokitlist.PokitListVer2
import pokitmons.pokit.core.ui.components.block.pokitlist.attributes.PokitListState
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.pookiempty.EmptyPooki
import pokitmons.pokit.core.ui.components.template.pookierror.ErrorPooki
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.uncategorized.components.FloatingBottomNavigation
import pokitmons.pokit.uncategorized.R.string as uncategorizedString
import pokitmons.pokit.core.ui.R.drawable as coreDrawable

@Composable
fun UncategorizedScreen(
    viewModel: UncategorizedViewModel
) {
    val state by viewModel.state.collectAsState()
    val linkList by viewModel.linkList.collectAsState()
    val linkListState by viewModel.linkListState.collectAsState()
    val pokitList by viewModel.pokitList.collectAsState()
    val pokitListState by viewModel.pokitListState.collectAsState()

    PokitBottomSheet(
        onHideBottomSheet = viewModel::hidePokitSelectBottomSheet,
        show = state.showPokitSelectBottomSheet,
        skipPartiallyExpanded = false
    ) {
        val lazyColumnListState = rememberLazyListState()
        val startPaging = remember {
            derivedStateOf {
                lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let { last ->
                    last.index >= lazyColumnListState.layoutInfo.totalItemsCount - 3
                } ?: false
            }
        }

        LaunchedEffect(Unit) {
            viewModel.refreshPokits()
        }

        LaunchedEffect(startPaging.value) {
            if (startPaging.value && pokitListState == PagingState.IDLE) {
                viewModel.loadNextPokits()
            }
        }

        LazyColumn(
            state = lazyColumnListState
        ) {
            items(
                items = pokitList
            ) { pokit ->
                PokitListVer2(
                    item = pokit,
                    title = pokit.title,
                    imageUrl = pokit.image,
                    sub = stringResource(id = uncategorizedString.count_format, pokit.count),
                    onClickItem = {
                        viewModel.moveSelectedLinks(it.id)
                    },
                    state = PokitListState.ACTIVE
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 12.dp)
            ) {
                IconButton(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterStart),
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = coreDrawable.icon_24_x),
                        contentDescription = "back button"
                    )
                }

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = uncategorizedString.title_classify_link),
                    style = PokitTheme.typography.title3.copy(color = PokitTheme.colors.textPrimary)
                )
            }

            val linkLazyColumnListState = rememberLazyListState()
            val startLinkPaging = remember {
                derivedStateOf {
                    linkLazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let { last ->
                        last.index >= linkLazyColumnListState.layoutInfo.totalItemsCount - 3
                    } ?: false
                }
            }

            LaunchedEffect(startLinkPaging.value) {
                if (startLinkPaging.value && linkListState == PagingState.IDLE) {
                    viewModel.loadNextLinks()
                }
            }

            when {
                (linkListState == PagingState.LOADING_INIT) -> {
                    LoadingProgress(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
                (linkListState == PagingState.FAILURE_INIT) -> {
                    ErrorPooki(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        title = stringResource(id = R.string.title_error),
                        sub = stringResource(id = R.string.sub_error)
                    )
                }
                (linkList.isEmpty()) -> {
                    EmptyPooki(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        title = stringResource(id = R.string.title_empty_links),
                        sub = stringResource(id = R.string.sub_empty_links)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        state = linkLazyColumnListState
                    ) {
                        items(
                            items = linkList,
                            key = { it.link.id }
                        ) { uncategorizedLink ->
                            LinkCard(
                                item = uncategorizedLink.link,
                                title = uncategorizedLink.link.title,
                                sub = "${uncategorizedLink.link.createdAt} · ${uncategorizedLink.link.domain}",
                                painter = rememberAsyncImagePainter(uncategorizedLink.link.thumbnail),
                                notRead = !uncategorizedLink.link.isRead,
                                badgeText = uncategorizedLink.link.memo,
                                onClickItem = {
                                    viewModel.toggleLinkSelected(it.id.toString())
                                },
                                modifier = Modifier.padding(20.dp),
                                checked = uncategorizedLink.isChecked
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                thickness = 1.dp,
                                color = PokitTheme.colors.borderTertiary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(88.dp))
                }
            }
        }

        // floating bottom area
        FloatingBottomNavigation(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            onClickSelectAll = viewModel::toggleAllLinksSelected,
            onClickRemoveLinks = viewModel::removeSelectedLinks,
            onClickMovePokit = viewModel::showPokitSelectBottomSheet
        )
    }
}
