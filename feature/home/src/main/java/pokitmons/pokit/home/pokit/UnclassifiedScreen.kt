package pokitmons.pokit.home.pokit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard

@Composable
fun UnclassifiedScreen(viewModel: PokitViewModel = hiltViewModel()) {
    viewModel.loadUnCategoryLinks()
    val unCategoryLinks = viewModel.unCategoryLinks.collectAsState()

    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(items = unCategoryLinks.value) { unCategoryDetail ->
            LinkCard(
                item = unCategoryDetail.linkType,
                title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
                sub = unCategoryDetail.createdAt,
                painter = rememberAsyncImagePainter(model = unCategoryDetail.imageUrl),
                notRead = !unCategoryDetail.isRead,
                badgeText = "미분류",
                onClickKebab = { },
                onClickItem = { }
            )
        }
    }
}

@Preview
@Composable
fun LinkCardPreview2() {
    UnclassifiedScreen()
}
