package pokitmons.pokit.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.components.searchitemlist.SearchItemList
import pokitmons.pokit.search.components.recentsearchword.RecentSearchWord
import pokitmons.pokit.search.components.toolbar.Toolbar
import pokitmons.pokit.search.model.sampleLinks

@Composable
fun SearchScreen(

) {
   Column(
       modifier = Modifier.fillMaxSize()
   ) {
       Toolbar()

       RecentSearchWord(
           recentSearchWords = listOf("샤프 노트북", "아이패드", "맥북", "돈없어", "LG 그램")
       )

       HorizontalDivider(
           thickness = 6.dp,
           color = PokitTheme.colors.backgroundPrimary
       )

       SearchItemList(
           modifier = Modifier.fillMaxWidth().weight(1f),
           links = sampleLinks
       )
   }
}
