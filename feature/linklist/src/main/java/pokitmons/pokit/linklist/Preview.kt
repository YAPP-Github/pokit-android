package pokitmons.pokit.linklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.linklist.model.LinkListScreenState

@Preview(showBackground = true)
@Composable
fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LinkListScreen(
                state = LinkListScreenState(),
                onBackPressed = { },
                loadNextLinkList = { },
                toggleSort = {}
            )
        }
    }
}
