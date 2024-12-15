package pokitmons.pokit.uncategorized.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable

@Composable
fun FloatingBottomNavigationButton(
    modifier: Modifier = Modifier,
    iconResourceId: Int,
    text: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.noRippleClickable(onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = iconResourceId),
            contentDescription = null,
            tint = PokitTheme.colors.inverseWh
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = text,
            style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.inverseWh)
        )
    }
}
