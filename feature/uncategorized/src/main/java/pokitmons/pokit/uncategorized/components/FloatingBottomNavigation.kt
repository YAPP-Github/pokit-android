package pokitmons.pokit.uncategorized.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.uncategorized.R.string as uncategorizedString
import pokitmons.pokit.core.ui.R.drawable as coreDrawable

@Composable
fun FloatingBottomNavigation(
    modifier: Modifier = Modifier,
    onClickSelectAll: () -> Unit,
    onClickRemoveLinks: () -> Unit,
    onClickMovePokit: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                shape = RoundedCornerShape(16.dp),
                color = PokitTheme.colors.brand
            )
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FloatingBottomNavigationButton(
            modifier = Modifier.padding(horizontal = 18.dp),
            iconResourceId = coreDrawable.icon_24_allcheck,
            text = stringResource(id = uncategorizedString.label_select_all),
            onClick = onClickSelectAll
        )

        FloatingBottomNavigationButton(
            modifier = Modifier.padding(horizontal = 18.dp),
            iconResourceId = coreDrawable.icon_24_trash,
            text = stringResource(id = uncategorizedString.label_remove_link),
            onClick = onClickRemoveLinks
        )

        FloatingBottomNavigationButton(
            modifier = Modifier.padding(horizontal = 18.dp),
            iconResourceId = coreDrawable.icon_24_movepokit,
            text = stringResource(id = uncategorizedString.label_move_pokit),
            onClick = onClickMovePokit
        )
    }
}
