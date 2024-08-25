package pokitmons.pokit.core.ui.components.template.bottomsheet.subcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable

@Composable
fun <T> PokitBottomSheetItem(
    text: String,
    resourceId: Int,
    data: T,
    onClick: (T) -> Unit,
) {
    Row(
        modifier = Modifier
            .noRippleClickable {
                onClick(data)
            }
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = PokitTheme.typography.body1Medium.copy(color = PokitTheme.colors.textSecondary)
        )

        Image(
            painter = painterResource(id = resourceId),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(color = PokitTheme.colors.iconPrimary)
        )
    }
}
