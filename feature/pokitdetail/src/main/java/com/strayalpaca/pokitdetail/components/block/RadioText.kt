package com.strayalpaca.pokitdetail.components.block

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun RadioText(
    selected: Boolean,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tintColor = getRadioTintColor(selected = selected)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = onClick
        )
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .border(
                    width = 2.dp,
                    color = tintColor,
                    shape = CircleShape
                )
        ) {
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .background(color = tintColor, shape = CircleShape)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary)
        )
    }
}

@Composable
private fun getRadioTintColor(
    selected: Boolean,
): Color {
    return if (selected) {
        PokitTheme.colors.brand
    } else {
        PokitTheme.colors.borderTertiary
    }
}
