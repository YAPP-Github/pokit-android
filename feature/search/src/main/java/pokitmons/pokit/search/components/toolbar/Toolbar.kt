package pokitmons.pokit.search.components.toolbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.input.PokitInput
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputIcon
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputIconPosition
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputShape
import pokitmons.pokit.core.ui.R.drawable as coreDrawable
import pokitmons.pokit.search.R.string as SearchString

@Composable
internal fun Toolbar(
    onClickBack: () -> Unit = {},
    inputSearchWord: (String) -> Unit = {},
    currentSearchWord: String = "",
    onClickSearch: () -> Unit = {},
    onClickRemove: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = onClickBack
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = coreDrawable.icon_24_arrow_left),
                contentDescription = "back button"
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        PokitInput(
            text = currentSearchWord,
            hintText = stringResource(id = SearchString.placeholder_input_search_word),
            onChangeText = inputSearchWord,
            shape = PokitInputShape.ROUND,
            icon = if (currentSearchWord.isNotEmpty()) {
                PokitInputIcon(
                    position = PokitInputIconPosition.RIGHT,
                    resourceId = coreDrawable.icon_24_x
                )
            } else {
                null
            },
            onClickIcon = remember {
                {
                    onClickRemove()
                    focusRequester.requestFocus()
                }
            },
            keyboardActions = remember {
                KeyboardActions {
                    onClickSearch()
                    focusManager.clearFocus()
                }
            },
            focusRequester = focusRequester
        )
    }
}
