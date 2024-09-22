package pokitmons.pokit.search.components.recentsearchword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RecentSearchWord(useAutoSave = false)

            RecentSearchWord(useAutoSave = true)

            RecentSearchWord(useAutoSave = true, recentSearchWords = listOf("Android", "IOS", "Server", "Design", "PM"))
        }
    }
}
