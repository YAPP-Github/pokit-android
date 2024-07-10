package com.strayalpaca.addlink

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.strayalpaca.addlink.components.block.Link
import com.strayalpaca.addlink.components.block.Toolbar
import com.strayalpaca.addlink.model.AddLinkScreenSideEffect
import com.strayalpaca.addlink.model.AddLinkScreenState
import com.strayalpaca.addlink.model.Pokit
import com.strayalpaca.addlink.model.ScreenStep
import com.strayalpaca.addlink.utils.BackPressHandler
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIcon
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIconPosition
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.inputarea.PokitInputArea
import pokitmons.pokit.core.ui.components.block.labeledinput.LabeledInput
import pokitmons.pokit.core.ui.components.block.pokitlist.PokitList
import pokitmons.pokit.core.ui.components.block.pokitlist.attributes.PokitListState
import pokitmons.pokit.core.ui.components.block.select.PokitSelect
import pokitmons.pokit.core.ui.components.block.switchradio.PokitSwitchRadio
import pokitmons.pokit.core.ui.components.block.switchradio.attributes.PokitSwitchRadioStyle
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun AddLinkScreenContainer(
    linkId: String?,
    viewModel: AddLinkViewModel,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    BackPressHandler(onBackPressed = viewModel::onBackPressed)

    LaunchedEffect(Unit) {
        linkId?.let {
            viewModel.loadPokitLink(it)
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            AddLinkScreenSideEffect.AddLinkSuccess -> {
                onBackPressed()
            }
            AddLinkScreenSideEffect.OnNavigationBack -> {
                onBackPressed()
            }
            is AddLinkScreenSideEffect.ToastMessage -> {
                Toast.makeText(context, context.getString(sideEffect.toastMessageEvent.stringResourceId), Toast.LENGTH_SHORT).show()
            }
        }
    }

    AddLinkScreen(
        isModifyLink = (linkId != null),
        state = state,
        inputUrl = viewModel::inputLinkUrl,
        inputTitle = viewModel::inputTitle,
        inputMemo = viewModel::inputMemo,
        inputNewPokitName = viewModel::inputNewPokitName,
        onClickAddPokit = viewModel::showAddPokitBottomSheet,
        onClickSavePokit = viewModel::savePokit,
        dismissPokitAddBottomSheet = viewModel::hideAddPokitBottomSheet,
        onClickSelectPokit = viewModel::showSelectPokitBottomSheet,
        onClickSelectPokitItem = viewModel::selectPokit,
        dismissPokitSelectBottomSheet = viewModel::hideSelectPokitBottomSheet,
        toggleRemindRadio = viewModel::setRemind,
        onBackPressed = viewModel::onBackPressed,
        onClickSaveButton = viewModel::saveLink
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddLinkScreen(
    isModifyLink: Boolean,
    state: AddLinkScreenState,
    inputUrl: (String) -> Unit,
    inputTitle: (String) -> Unit,
    inputMemo: (String) -> Unit,
    inputNewPokitName: (String) -> Unit,
    onClickAddPokit: () -> Unit,
    onClickSavePokit: () -> Unit,
    dismissPokitAddBottomSheet: () -> Unit,
    onClickSelectPokit: () -> Unit,
    onClickSelectPokitItem: (Pokit) -> Unit,
    dismissPokitSelectBottomSheet: () -> Unit,
    toggleRemindRadio: (Boolean) -> Unit,
    onBackPressed: () -> Unit,
    onClickSaveButton: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val enable = remember(state.step) {
        !(state.step == ScreenStep.SAVE_LOADING || state.step == ScreenStep.LOADING || state.step == ScreenStep.POKIT_ADD_LOADING)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar(
            modifier = Modifier.fillMaxWidth(),
            onClickBack = onBackPressed,
            title = if (isModifyLink) stringResource(id = R.string.modify_link) else stringResource(id = R.string.add_link)
        )

        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 20.dp)
                    .verticalScroll(
                        state = scrollState,
                        flingBehavior = null
                    )
            ) {
                if (state.link != null) {
                    Link(state.link)
                }

                Spacer(modifier = Modifier.height(16.dp))

                LabeledInput(
                    label = stringResource(id = R.string.link),
                    sub = "",
                    maxLength = null,
                    inputText = state.linkUrl,
                    hintText = stringResource(id = R.string.placeholder_link),
                    onChangeText = inputUrl,
                    enable = enable
                )

                Spacer(modifier = Modifier.height(24.dp))

                LabeledInput(
                    label = stringResource(id = R.string.title),
                    sub = "",
                    maxLength = 20,
                    inputText = state.title,
                    hintText = stringResource(id = R.string.placeholder_title),
                    onChangeText = inputTitle,
                    enable = enable
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    PokitSelect(
                        text = if (state.currentPokit == null) stringResource(id = R.string.uncategorized) else state.currentPokit.title,
                        hintText = stringResource(id = R.string.uncategorized),
                        label = stringResource(id = R.string.pokit),
                        modifier = Modifier.weight(1f),
                        onClick = onClickSelectPokit,
                        enable = enable
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    PokitButton(
                        text = null,
                        icon = PokitButtonIcon(
                            resourceId = pokitmons.pokit.core.ui.R.drawable.icon_24_plus,
                            position = PokitButtonIconPosition.LEFT
                        ),
                        size = PokitButtonSize.LARGE,
                        onClick = onClickAddPokit,
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
                    onChangeText = inputMemo,
                    enable = enable
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${state.memo.length}/100",
                    style = PokitTheme.typography.detail1.copy(color = PokitTheme.colors.textTertiary),
                    textAlign = TextAlign.End
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(id = R.string.title_remind),
                    style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary)
                )

                Spacer(modifier = Modifier.height(12.dp))

                PokitSwitchRadio(
                    modifier = Modifier.fillMaxWidth(),
                    itemList = listOf(
                        Pair(stringResource(id = R.string.reject_remind), false),
                        Pair(stringResource(id = R.string.accept_remind), true)
                    ),
                    style = PokitSwitchRadioStyle.STROKE,
                    selectedItem = if (state.useRemind) {
                        Pair(stringResource(id = R.string.accept_remind), true)
                    } else {
                        Pair(stringResource(id = R.string.reject_remind), false)
                    },
                    onClickItem = {
                        toggleRemindRadio(it.second)
                    },
                    getTitleFromItem = { it.first },
                    enabled = enable
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.sub_remind),
                    style = PokitTheme.typography.detail1.copy(color = PokitTheme.colors.textTertiary)
                )

                Spacer(modifier = Modifier.height(32.dp))

                PokitButton(
                    text = stringResource(id = R.string.save),
                    icon = null,
                    onClick = onClickSaveButton,
                    modifier = Modifier.fillMaxWidth(),
                    size = PokitButtonSize.LARGE,
                    enable = state.saveButtonEnable()
                )
            }
        }

        if (state.step == ScreenStep.POKIT_SELECT) {
            PokitBottomSheet(onHideBottomSheet = dismissPokitSelectBottomSheet) {
                LazyColumn {
                    items(
                        items = state.pokitList
                    ) {
                        PokitList(
                            item = it,
                            title = it.title,
                            sub = stringResource(id = R.string.count_format, it.count),
                            onClickKebab = onClickSelectPokitItem,
                            onClickItem = onClickSelectPokitItem,
                            state = PokitListState.ACTIVE
                        )
                    }
                }
            }
        }

        if (state.step == ScreenStep.POKIT_ADD) {
            PokitBottomSheet(onHideBottomSheet = dismissPokitAddBottomSheet) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    LabeledInput(
                        label = "",
                        inputText = state.pokitAddInput,
                        hintText = stringResource(id = R.string.placeholder_input_pokit_name),
                        onChangeText = inputNewPokitName,
                        maxLength = 10
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PokitButton(
                        text = stringResource(id = R.string.add),
                        icon = null,
                        onClick = onClickSavePokit,
                        modifier = Modifier.fillMaxWidth(),
                        size = PokitButtonSize.LARGE,
                        enable = state.pokitAddInput.isNotEmpty()
                    )
                }
            }
        }
    }
}
