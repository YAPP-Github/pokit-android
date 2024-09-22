package pokitmons.pokit.alarm.components.toolbar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pokitmons.pokit.alarm.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
internal fun Preview() {
    PokitTheme {
        Column {
            Toolbar(onClickBack = {}, title = stringResource(id = R.string.alarm_box))
        }
    }
}
