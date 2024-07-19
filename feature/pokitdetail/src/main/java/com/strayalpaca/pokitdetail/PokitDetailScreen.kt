package com.strayalpaca.pokitdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalpaca.pokitdetail.components.block.TitleArea
import com.strayalpaca.pokitdetail.components.block.Toolbar
import com.strayalpaca.pokitdetail.components.template.filterselectbottomsheet.FilterSelectBottomSheet
import com.strayalpaca.pokitdetail.components.template.linkdetailbottomsheet.LinkDetailBottomSheet
import com.strayalpaca.pokitdetail.model.BottomSheetType
import com.strayalpaca.pokitdetail.model.Filter
import com.strayalpaca.pokitdetail.model.Link
import com.strayalpaca.pokitdetail.model.Pokit
import com.strayalpaca.pokitdetail.model.PokitDetailScreenState
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.core.ui.components.block.pokitlist.PokitList
import pokitmons.pokit.core.ui.components.block.pokitlist.attributes.PokitListState
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.modifybottomsheet.ModifyBottomSheetContent
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.RemoveItemBottomSheetContent
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.attributes.RemoveItemType
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.drawable as coreDrawable

@Composable
fun PokitDetailScreenContainer(
    viewModel: PokitDetailViewModel,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val linkList by viewModel.linkList.collectAsState()
    val pokitList by viewModel.pokitList.collectAsState()

    PokitDetailScreen(
        onBackPressed = onBackPressed,
        onClickFilter = viewModel::showFilterChangeBottomSheet,
        hideFilterChangeBottomSheet = viewModel::hideFilterChangeBottomSheet,
        changeFilter = viewModel::changeFilter,
        showPokitSelectBottomSheet = viewModel::showPokitSelectBottomSheet,
        hidePokitSelectBottomSheet = viewModel::hidePokitSelectBottomSheet,
        changePokit = viewModel::changePokit,
        showPokitModifyBottomSheet = viewModel::showPokitModifyBottomSheet,
        showPokitRemoveBottomSheet = viewModel::showPokitRemoveBottomSheet,
        hidePokitModifyBottomSheet = viewModel::hidePokitBottomSheet,
        showLinkModifyBottomSheet = viewModel::showLinkModifyBottomSheet,
        showLinkRemoveBottomSheet = viewModel::showLinkRemoveBottomSheet,
        hideLinkModifyBottomSheet = viewModel::hideLinkBottomSheet,
        hideLinkDetailBottomSheet = viewModel::hideLinkDetailBottomSheet,
        state = state,
        linkList = linkList,
        pokitList = pokitList,
        onClickLink = viewModel::showLinkDetailBottomSheet
    )
}

@Composable
fun PokitDetailScreen(
    onBackPressed: () -> Unit = {},
    onClickFilter: () -> Unit = {},
    hideFilterChangeBottomSheet: () -> Unit = {},
    changeFilter: (Filter) -> Unit = {},
    showPokitSelectBottomSheet: () -> Unit = {},
    hidePokitSelectBottomSheet: () -> Unit = {},
    changePokit: (Pokit) -> Unit = {},
    showPokitModifyBottomSheet: () -> Unit = {},
    showPokitRemoveBottomSheet: () -> Unit = {},
    hidePokitModifyBottomSheet: () -> Unit = {},
    showLinkModifyBottomSheet: (Link) -> Unit = {},
    showLinkRemoveBottomSheet: () -> Unit = {},
    hideLinkModifyBottomSheet: () -> Unit = {},
    hideLinkDetailBottomSheet: () -> Unit = {},
    state: PokitDetailScreenState = PokitDetailScreenState(),
    linkList: List<Link> = emptyList(),
    pokitList: List<Pokit> = emptyList(),
    onClickLink: (Link) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar(
            onBackPressed = onBackPressed,
            onClickKebab = showPokitModifyBottomSheet
        )

        Spacer(modifier = Modifier.height(12.dp))

        TitleArea(
            title = state.currentPokit.title,
            sub = stringResource(id = pokitmons.pokit.core.ui.R.string.pokit_count_format, state.currentPokit.count),
            onClickSelectPokit = showPokitSelectBottomSheet,
            onClickSelectFilter = onClickFilter
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(linkList) { link ->
                LinkCard(
                    item = link,
                    title = link.title,
                    sub = "${link.dateString} · ${link.domainUrl}",
                    painter = painterResource(id = coreDrawable.icon_24_google),
                    notRead = link.isRead,
                    badgeText = stringResource(id = link.linkType.textResourceId),
                    onClickKebab = showLinkModifyBottomSheet,
                    onClickItem = onClickLink,
                    modifier = Modifier.padding(20.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    thickness = 1.dp,
                    color = PokitTheme.colors.borderTertiary
                )
            }
        }

        if (state.linkDetailBottomSheetVisible && state.currentLink != null) {
            LinkDetailBottomSheet(link = state.currentLink, onHideBottomSheet = hideLinkDetailBottomSheet)
        }

        if (state.filterChangeBottomSheetVisible) {
            FilterSelectBottomSheet(
                filter = state.currentFilter,
                onHideRequest = hideFilterChangeBottomSheet,
                onFilterChange = changeFilter
            )
        }

        if (state.pokitSelectBottomSheetVisible) {
            PokitBottomSheet(onHideBottomSheet = hidePokitSelectBottomSheet) {
                LazyColumn {
                    items(
                        items = pokitList
                    ) { pokit ->
                        PokitList(
                            item = pokit,
                            title = pokit.title,
                            sub = stringResource(id = R.string.link_count_format, pokit.count),
                            onClickItem = changePokit,
                            state = PokitListState.ACTIVE
                        )
                    }
                }
            }
        }

        if (state.linkBottomSheetType != null) {
            PokitBottomSheet(onHideBottomSheet = hideLinkModifyBottomSheet) {
                when (state.linkBottomSheetType) {
                    BottomSheetType.MODIFY -> {
                        ModifyBottomSheetContent(
                            onClickShare = {},
                            onClickModify = {},
                            onClickRemove = showLinkRemoveBottomSheet
                        )
                    }
                    BottomSheetType.REMOVE -> {
                        RemoveItemBottomSheetContent(
                            removeItemType = RemoveItemType.LINK,
                            onClickCancel = hideLinkModifyBottomSheet,
                            onClickRemove = {}
                        )
                    }
                }
            }
        }

        if (state.pokitBottomSheetType != null) {
            PokitBottomSheet(onHideBottomSheet = hidePokitModifyBottomSheet) {
                when (state.pokitBottomSheetType) {
                    BottomSheetType.MODIFY -> {
                        ModifyBottomSheetContent(
                            onClickShare = {},
                            onClickModify = {},
                            onClickRemove = showPokitRemoveBottomSheet
                        )
                    }
                    BottomSheetType.REMOVE -> {
                        RemoveItemBottomSheetContent(
                            removeItemType = RemoveItemType.POKIT,
                            onClickCancel = hidePokitModifyBottomSheet,
                            onClickRemove = {}
                        )
                    }
                }
            }
        }
    }
}
