package pokitmons.pokit.home.pokit

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.strayalpaca.pokitdetail.R
import com.strayalpaca.pokitdetail.model.BottomSheetType
import com.strayalpaca.pokitdetail.paging.SimplePagingState
import pokitmons.pokit.core.ui.components.atom.loading.LoadingProgress
import pokitmons.pokit.core.ui.components.block.pokitcard.PokitCard
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.modifybottomsheet.ModifyBottomSheetContent
import pokitmons.pokit.core.ui.components.template.pokkiempty.EmptyPokki
import pokitmons.pokit.core.ui.components.template.pokkierror.ErrorPokki
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent
import pokitmons.pokit.core.ui.R.string as coreString

@Composable
fun PokitScreen(
    modifier: Modifier = Modifier,
    viewModel: PokitViewModel,
    onNavigateToPokitDetail: (String) -> Unit,
    onNavigateToLinkModify: (String) -> Unit,
    onNavigateToPokitModify: (String) -> Unit,
) {
    val pokits = viewModel.pokits.collectAsState()
    val pokitsState by viewModel.pokitsState.collectAsState()
    val selectedCategory by viewModel.selectedCategory
    val unCategoryLinks = viewModel.unCategoryLinks.collectAsState()
    val unCategoryLinksState by viewModel.linksState.collectAsState()

    val pokitOptionBottomSheetType by viewModel.pokitOptionBottomSheetType.collectAsState()
    val currentDetailSelectedCategory by viewModel.currentDetailSelectedCategory.collectAsState()

    val context: Context = LocalContext.current

    PokitBottomSheet(
        onHideBottomSheet = viewModel::hidePokitDetailRemoveBottomSheet,
        show = pokitOptionBottomSheetType != null
    ) {
        when (pokitOptionBottomSheetType) {
            BottomSheetType.MODIFY -> {
                ModifyBottomSheetContent(
                    onClickShare = { Toast.makeText(context, "준비중입니다.", Toast.LENGTH_SHORT).show() },
                    onClickModify = remember {
                        {
                            viewModel.hidePokitDetailRemoveBottomSheet()
                            onNavigateToPokitModify(currentDetailSelectedCategory!!.id)
                        }
                    },
                    onClickRemove = viewModel::showPokitDetailRemoveBottomSheet
                )
            }
            BottomSheetType.REMOVE -> {
                TwoButtonBottomSheetContent(
                    title = stringResource(id = R.string.title_remove_pokit),
                    subText = stringResource(id = R.string.sub_remove_pokit),
                    onClickLeftButton = viewModel::hidePokitDetailRemoveBottomSheet,
                    onClickRightButton = remember {
                        {
                            viewModel.removeCurrentDetailSelectedCategory()
                            viewModel.hidePokitDetailRemoveBottomSheet()
                        }
                    }
                )
            }
            else -> {}
        }
    }

    Column(
        modifier = modifier
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        HomeMid(viewModel = viewModel)

        when (selectedCategory) {
            is Category.Pokit -> {
                when {
                    (pokitsState == SimplePagingState.LOADING_INIT) -> {
                        LoadingProgress(modifier = Modifier.fillMaxSize())
                    }
                    (pokitsState == SimplePagingState.FAILURE_INIT) -> {
                        ErrorPokki(
                            modifier = Modifier.fillMaxSize(),
                            title = stringResource(id = coreString.title_error),
                            sub = stringResource(id = coreString.sub_error),
                            onClickRetry = viewModel::loadPokits
                        )
                    }
                    (pokits.value.isEmpty()) -> {
                        EmptyPokki(
                            modifier = Modifier.fillMaxSize(),
                            title = stringResource(id = coreString.title_empty_pokits),
                            sub = stringResource(id = coreString.sub_empty_pokits)
                        )
                    }
                    else -> {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(bottom = 100.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = pokits.value,
                                key = { it.id }
                            ) { pokitDetail ->
                                PokitCard(
                                    text = pokitDetail.title,
                                    linkCount = pokitDetail.count,
                                    painter = rememberAsyncImagePainter(model = pokitDetail.image.url),
                                    onClick = { onNavigateToPokitDetail(pokitDetail.id) },
                                    onClickKebab = {
                                        viewModel.showPokitDetailOptionBottomSheet(pokitDetail)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            is Category.Unclassified -> {
                when {
                    (unCategoryLinksState == SimplePagingState.LOADING_INIT) -> {
                        LoadingProgress(modifier = Modifier.fillMaxSize())
                    }
                    (unCategoryLinksState == SimplePagingState.FAILURE_INIT) -> {
                        ErrorPokki(
                            modifier = Modifier.fillMaxSize(),
                            title = stringResource(id = coreString.title_error),
                            sub = stringResource(id = coreString.sub_error),
                            onClickRetry = viewModel::loadUnCategoryLinks
                        )
                    }
                    (unCategoryLinks.value.isEmpty()) -> {
                        EmptyPokki(
                            modifier = Modifier.fillMaxSize(),
                            title = stringResource(id = coreString.title_empty_links),
                            sub = stringResource(id = coreString.sub_empty_links)
                        )
                    }
                    else -> {
                        UnclassifiedScreen(
                            viewModel = viewModel,
                            onNavigateToLinkModify = onNavigateToLinkModify
                        )
                    }
                }
            }
        }
    }
}
