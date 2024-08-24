package com.strayalpaca.addpokit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.strayalpaca.addpokit.components.atom.PokitProfileImage
import com.strayalpaca.addpokit.components.block.Toolbar
import com.strayalpaca.addpokit.model.AddPokitScreenState
import com.strayalpaca.addpokit.model.AddPokitScreenStep
import com.strayalpaca.addpokit.model.AddPokitSideEffect
import com.strayalpaca.addpokit.model.Pokit
import com.strayalpaca.addpokit.model.PokitImage
import com.strayalpaca.addpokit.paging.SimplePagingState
import com.strayalpaca.addpokit.utils.BackPressHandler
import org.orbitmvi.orbit.compose.collectSideEffect
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.block.labeledinput.LabeledInput
import pokitmons.pokit.core.ui.components.block.pokitlist.PokitList
import pokitmons.pokit.core.ui.components.block.pokitlist.attributes.PokitListState
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.drawable as coreDrawable
import pokitmons.pokit.core.ui.R.string as coreString

@Composable
fun AddPokitScreenContainer(
    viewModel: AddPokitViewModel,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val pokitName by viewModel.pokitName.collectAsState()
    val images by viewModel.pokitImages.collectAsState()
    val pokits by viewModel.pokitList.collectAsState()
    val pokitsState by viewModel.pokitListState.collectAsState()

    val saveButtonEnable = remember {
        derivedStateOf {
            state.step != AddPokitScreenStep.POKIT_SAVE_LOADING &&
                state.pokitInputErrorMessage == null &&
                state.pokitImage != null
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            AddPokitSideEffect.OnNavigationBack -> {
                onBackPressed()
            }
        }
    }

    BackPressHandler(onBackPressed = viewModel::onBackPressed)

    AddPokitScreen(
        pokitName = pokitName,
        state = state,
        saveButtonEnable = saveButtonEnable.value,
        onclickAddPokit = viewModel::savePokit,
        inputPokitName = viewModel::inputPokitName,
        onBackPressed = viewModel::onBackPressed,
        hideProfileSelectBottomSheet = viewModel::hidePokitProfileSelectBottomSheet,
        showSelectProfileBottomSheet = viewModel::showPokitProfileSelectBottomSheet,
        selectPokitProfileImage = viewModel::selectPokitProfile,
        pokits = pokits,
        pokitsState = pokitsState,
        loadPokits = viewModel::loadPokitList,
        pokitImages = images
    )
}

@Composable
fun AddPokitScreen(
    pokitName: String = "",
    state: AddPokitScreenState = AddPokitScreenState(),
    saveButtonEnable: Boolean = true,
    onclickAddPokit: () -> Unit = {},
    inputPokitName: (String) -> Unit = {},
    onBackPressed: () -> Unit = {},
    hideProfileSelectBottomSheet: () -> Unit = {},
    showSelectProfileBottomSheet: () -> Unit = {},
    selectPokitProfileImage: (PokitImage) -> Unit = {},
    pokits: List<Pokit> = emptyList(),
    pokitsState: SimplePagingState = SimplePagingState.IDLE,
    loadPokits: () -> Unit = {},
    pokitImages: List<PokitImage> = emptyList(),
    pokitImagesState: SimplePagingState = SimplePagingState.IDLE,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PokitTheme.colors.backgroundBase),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Toolbar(
            onClickBack = onBackPressed,
            title = stringResource(id = if (state.isModify) R.string.title_modify_pokit else R.string.title_add_pokit)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.size(80.dp)) {
            AsyncImage(
                model = state.pokitImage?.url,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 7.dp, y = (-7).dp)
                    .size(24.dp)
                    .background(
                        color = PokitTheme.colors.inverseWh,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = PokitTheme.colors.borderSecondary,
                        shape = CircleShape
                    )
                    .clip(
                        shape = CircleShape
                    )
                    .clickable(
                        onClick = showSelectProfileBottomSheet
                    )
                    .padding(3.dp)
            ) {
                Image(
                    painter = painterResource(id = coreDrawable.icon_24_edit),
                    contentDescription = "null",
                    modifier = Modifier
                        .size(18.dp),
                    colorFilter = ColorFilter.tint(
                        color = PokitTheme.colors.iconTertiary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LabeledInput(
            modifier = Modifier.padding(horizontal = 20.dp),
            label = stringResource(id = R.string.pokit_name),
            inputText = pokitName,
            hintText = stringResource(id = R.string.placeholder_pokit_name),
            onChangeText = inputPokitName,
            isError = state.pokitInputErrorMessage != null,
            sub = state.pokitInputErrorMessage ?: "",
            enable = (state.step != AddPokitScreenStep.POKIT_SAVE_LOADING),
            maxLength = 10
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = stringResource(id = R.string.my_pokit),
            style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            val pokitLazyColumnListState = rememberLazyListState()
            val startPokitPaging = remember {
                derivedStateOf {
                    pokitLazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let { last ->
                        last.index >= pokitLazyColumnListState.layoutInfo.totalItemsCount - 3
                    } ?: false
                }
            }

            LaunchedEffect(startPokitPaging.value) {
                if (startPokitPaging.value && pokitsState == SimplePagingState.IDLE) {
                    loadPokits()
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = pokitLazyColumnListState
            ) {
                items(pokits) { item: Pokit ->
                    PokitList(
                        item = item,
                        title = item.title,
                        sub = stringResource(id = coreString.pokit_count_format, item.count),
                        onClickItem = {},
                        state = PokitListState.DISABLE
                    )
                }
            }

            if (pokitImagesState == SimplePagingState.LOADING_INIT) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = PokitTheme.colors.brand,
                    trackColor = PokitTheme.colors.backgroundSecondary
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            PokitButton(
                text = stringResource(id = R.string.save),
                icon = null,
                onClick = onclickAddPokit,
                modifier = Modifier.fillMaxWidth(),
                size = PokitButtonSize.LARGE,
                enable = saveButtonEnable
            )
        }

        PokitBottomSheet(
            onHideBottomSheet = hideProfileSelectBottomSheet,
            show = state.step == AddPokitScreenStep.SELECT_PROFILE
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 52.dp),
                columns = GridCells.Adaptive(66.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pokitImages) { pokitImage ->
                    PokitProfileImage(
                        pokitImage = pokitImage,
                        onClick = selectPokitProfileImage,
                        focused = (state.pokitImage?.id == pokitImage.id)
                    )
                }
            }
        }
    }
}
