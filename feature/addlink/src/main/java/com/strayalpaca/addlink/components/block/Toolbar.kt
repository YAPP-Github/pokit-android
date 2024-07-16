package com.strayalpaca.addlink.components.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun Toolbar(
    onClickBack: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = onClickBack
        ) {
            Icon(
                painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_arrow_left),
                contentDescription = "back button"
            )
        }

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = PokitTheme.typography.title3.copy(color = PokitTheme.colors.textPrimary),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(48.dp))
    }
}
