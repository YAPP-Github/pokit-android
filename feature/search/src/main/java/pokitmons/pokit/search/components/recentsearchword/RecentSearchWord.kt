package pokitmons.pokit.search.components.recentsearchword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.chip.PokitChip
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipIconPosiion
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.R.string as SearchString

@Composable
internal fun RecentSearchWord(
    onClickRemoveAll: () -> Unit = {},
    onToggleAutoSave: () -> Unit = {},
    useAutoSave: Boolean = true,
    recentSearchWords: List<String> = emptyList(),
    onClickRemoveSearchWord: (String) -> Unit = {},
    onClickSearchWord: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = SearchString.recent_search_word),
                style = PokitTheme.typography.body2Bold.copy(color = PokitTheme.colors.textPrimary)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onClickRemoveAll
                    )
                    .padding(4.dp),
                text = stringResource(id = SearchString.remove_all),
                style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.textTertiary)
            )

            Text(
                text = "|",
                style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.textTertiary)
            )

            Text(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onToggleAutoSave
                    )
                    .padding(4.dp),
                text = stringResource(
                    id = if (useAutoSave) SearchString.off_use_auto_save else SearchString.on_auto_save
                ),
                style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.textTertiary)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (!useAutoSave) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 7.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = SearchString.describe_auto_save_is_off),
                style = PokitTheme.typography.body3Regular.copy(color = PokitTheme.colors.textTertiary)
            )
        } else if (recentSearchWords.isEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 7.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = SearchString.describe_recent_search_word_is_empty),
                style = PokitTheme.typography.body3Regular.copy(color = PokitTheme.colors.textTertiary)
            )
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(recentSearchWords) { searchWord ->
                    PokitChip(
                        data = searchWord,
                        text = searchWord,
                        removeIconPosition = PokitChipIconPosiion.RIGHT,
                        onClickRemove = onClickRemoveSearchWord,
                        onClickItem = onClickSearchWord
                    )
                }
            }
        }

    }
}
