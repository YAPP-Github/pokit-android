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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalpaca.pokitdetail.components.block.TitleArea
import com.strayalpaca.pokitdetail.components.block.Toolbar
import com.strayalpaca.pokitdetail.model.Link
import com.strayalpaca.pokitdetail.model.Pokit
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.drawable as coreDrawable

@Composable
fun PokitDetailScreenContainer() {

}

@Composable
fun PokitDetailScreen(
    onBackPressed: () -> Unit = {},
    onClickToolbarKebab: () -> Unit = {},
    onClickFilter: () -> Unit = {},
    showPokitSelectBottomSheet: () -> Unit = {},
    changePokit: (Pokit) -> Unit = {},
    currentPokit: Pokit? = null,
    linkList: List<Link> = emptyList(),
    onClickLinkKebab: (Link) -> Unit = {},
    onClickLink: (Link) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar(
            onBackPressed = onBackPressed,
            onClickKebab = onClickToolbarKebab
        )

        Spacer(modifier = Modifier.height(12.dp))

        TitleArea(
            title = currentPokit?.title ?: "",
            sub = stringResource(id = pokitmons.pokit.core.ui.R.string.pokit_count_format, currentPokit?.count ?: 0),
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
                    onClickKebab = onClickLinkKebab,
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
    }
}
