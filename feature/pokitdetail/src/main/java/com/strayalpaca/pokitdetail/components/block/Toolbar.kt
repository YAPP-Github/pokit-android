package com.strayalpaca.pokitdetail.components.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R.drawable as coreDrawable

@Composable
internal fun Toolbar(
    onBackPressed: () -> Unit,
    onClickKebab: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = onBackPressed
        ) {
            Icon(
                painter = painterResource(id = coreDrawable.icon_24_arrow_left),
                contentDescription = "back button"
            )
        }

        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = onClickKebab
        ) {
            Icon(
                painter = painterResource(id = coreDrawable.icon_24_kebab),
                contentDescription = "back button"
            )
        }
    }
}
