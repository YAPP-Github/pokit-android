package pokitmons.pokit.terms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.checkbox.PokitCheckbox
import pokitmons.pokit.core.ui.components.atom.checkbox.attributes.PokitCheckboxStyle
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun TermsCheckBoxItem(
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        PokitCheckbox(
            checked = false,
            style = PokitCheckboxStyle.ICON_ONLY,
            onClick = { }
        )

        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = text,
            style = PokitTheme.typography.body2Medium
        )

        Icon(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_arrow_right),
            contentDescription = "화살표"
        )
    }
}
