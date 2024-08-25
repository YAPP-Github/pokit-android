package pokitmons.pokit.terms

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.checkbox.PokitCheckbox
import pokitmons.pokit.core.ui.components.atom.checkbox.attributes.PokitCheckboxStyle
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable

@Composable
fun TermsCheckBoxItem(
    url: String,
    text: String,
    isChecked: Boolean,
    click: () -> Unit,
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            PokitCheckbox(
                checked = isChecked,
                style = PokitCheckboxStyle.ICON_ONLY,
                onClick = { click() }
            )

            Text(
                modifier = Modifier
                    .noRippleClickable { moveToUrl(url, context) }
                    .padding(start = 4.dp),
                text = text,
                style = PokitTheme.typography.body2Medium
            )
        }
        Icon(
            modifier = Modifier.align(Alignment.CenterEnd),
            painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_arrow_right),
            contentDescription = "화살표"
        )
    }
}

fun moveToUrl(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
