package com.strayalpaca.pokitdetail.components.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalpaca.pokitdetail.R
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIcon
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIconPosition
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonShape
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.drawable as coreDrawable

@Composable
internal fun TitleArea(
    title: String,
    sub: String,
    onClickSelectPokit: () -> Unit,
    onClickSelectFilter: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = PokitTheme.typography.title1.copy(color = PokitTheme.colors.textPrimary)
            )

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp),
                onClick = onClickSelectPokit
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = coreDrawable.icon_24_arrow_down),
                    contentDescription = "change pokit"
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sub,
                style = PokitTheme.typography.detail1.copy(color = PokitTheme.colors.textSecondary)
            )

            PokitButton(
                text = stringResource(id = R.string.filter),
                icon = PokitButtonIcon(
                    resourceId = coreDrawable.icon_24_filter,
                    position = PokitButtonIconPosition.LEFT
                ),
                onClick = onClickSelectFilter,
                shape = PokitButtonShape.ROUND,
                size = PokitButtonSize.SMALL
            )
        }
    }
}
