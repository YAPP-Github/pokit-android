package com.strayalpaca.addlink

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.strayalpaca.addlink.components.block.Link
import com.strayalpaca.addlink.components.block.LoadingLink
import com.strayalpaca.addlink.components.block.Toolbar
import com.strayalpaca.addlink.model.AddLinkScreenSideEffect
import com.strayalpaca.addlink.model.ScreenStep
import com.strayalpaca.addlink.model.ToastMessageEvent
import pokitmons.pokit.core.feature.flow.collectAsEffect
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.inputarea.PokitInputArea
import pokitmons.pokit.core.ui.components.block.labeledinput.LabeledInput
import pokitmons.pokit.core.ui.components.block.pokitlist.PokitListVer2
import pokitmons.pokit.core.ui.components.block.pokitlist.attributes.PokitListState
import pokitmons.pokit.core.ui.components.block.pokittoast.PokitToast
import pokitmons.pokit.core.ui.components.block.select.PokitSelect
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun AddLinkScreenContainer(
    viewModel: AddLinkViewModel,
    onBackPressed: () -> Unit,
    onNavigateToAddPokit: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    viewModel.sideEffect.collectAsEffect { sideEffect ->
        when (sideEffect) {
            AddLinkScreenSideEffect.NavigationEvent.AddPokit -> {
                onNavigateToAddPokit()
            }
            AddLinkScreenSideEffect.NavigationEvent.Back -> {
                onBackPressed()
            }
        }
    }
    val pokitList by viewModel.pokitList.collectAsState()
    val pokitListState by viewModel.pokitListState.collectAsState()

    PokitBottomSheet(
        onHideBottomSheet = viewModel::hidePokitListBottomSheet,
        show = state.step == ScreenStep.POKIT_SELECT,
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = viewModel::checkPokitCountThenNavigateToAddPokit
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.padding(start = 30.dp),
                painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.image_add_pokit),
                contentDescription = "포킷 추가 버튼"
            )

            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = "포킷 추가하기",
                style = PokitTheme.typography.body1Bold
            )
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = PokitTheme.colors.borderTertiary
        )

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
                    sub = stringResource(id = R.string.count_format, pokit.count),
                    onClickItem = viewModel::setSelectedPokit,
                    state = PokitListState.ACTIVE
                )
            }
        }
    }

    AddLinkScreen(
        onBackPressed = onBackPressed,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddLinkScreen(
    onBackPressed: () -> Unit,
    viewModel: AddLinkViewModel,
) {
    val state by viewModel.state.collectAsState()

    val scrollState = rememberScrollState()
    val enable = remember(state.step) {
        !(
            state.step == ScreenStep.SAVE_LOADING ||
                state.step == ScreenStep.LOADING ||
                state.step == ScreenStep.POKIT_ADD_LOADING
            )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PokitTheme.colors.backgroundBase)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Toolbar(
            modifier = Modifier.fillMaxWidth(),
            onClickBack = onBackPressed,
            title = if (state.isModifyLink) stringResource(id = R.string.modify_link) else stringResource(id = R.string.add_link)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .verticalScroll(
                            state = scrollState,
                            flingBehavior = null
                        )
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    if (state.step == ScreenStep.LINK_LOADING) {
                        LoadingLink()
                        Spacer(modifier = Modifier.height(16.dp))
                    } else if (state.link != null) {
                        Link(link = state.link!!, title = state.title.ifEmpty { null })
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    LabeledInput(
                        label = stringResource(id = R.string.link),
                        sub = "",
                        maxLength = null,
                        inputText = state.linkUrl,
                        hintText = stringResource(id = R.string.placeholder_link),
                        onChangeText = viewModel::inputLinkUrl,
                        enable = enable,
                        onClickRemove = viewModel::clearLinkUrl
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    LabeledInput(
                        label = stringResource(id = R.string.title),
                        sub = "",
                        inputText = state.title,
                        hintText = stringResource(id = R.string.placeholder_title),
                        onChangeText = viewModel::inputTitle,
                        enable = enable,
                        onClickRemove = viewModel::clearTitle
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        PokitSelect(
                            text = if (state.currentPokit == null) stringResource(id = R.string.uncategorized) else state.currentPokit!!.title,
                            hintText = stringResource(id = R.string.uncategorized),
                            label = stringResource(id = R.string.pokit),
                            modifier = Modifier.weight(1f),
                            onClick = viewModel::showPokitListBottomSheet,
                            enable = enable
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = stringResource(id = R.string.memo),
                        style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PokitInputArea(
                        text = state.memo,
                        hintText = stringResource(id = R.string.placeholder_memo),
                        onChangeText = viewModel::inputMemo,
                        enable = enable,
                        isError = state.memo.length >= state.memoMaxLength
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (state.memo.length >= state.memoMaxLength) {
                            Text(
                                color = PokitTheme.colors.error,
                                text = stringResource(id = R.string.memo_count_format, state.memoMaxLength),
                                style = PokitTheme.typography.detail1
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            color = if (state.memo.length >= state.memoMaxLength) PokitTheme.colors.error else PokitTheme.colors.textTertiary,
                            modifier = Modifier.weight(1f),
                            text = "${state.memo.length}/${state.memoMaxLength}",
                            style = PokitTheme.typography.detail1,
                            textAlign = TextAlign.End
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            state.toastMessage?.let { toastMessageEvent: ToastMessageEvent ->
                PokitToast(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(start = 12.dp, end = 12.dp, bottom = 16.dp),
                    text = stringResource(id = toastMessageEvent.stringResourceId),
                    onClickClose = viewModel::hideToastMessage
                )
            }
        }

        Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)) {
            PokitButton(
                text = stringResource(id = R.string.save),
                icon = null,
                onClick = viewModel::saveLink,
                modifier = Modifier.fillMaxWidth(),
                size = PokitButtonSize.LARGE
            )
        }
    }
}
