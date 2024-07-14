package com.strayalpaca.addpokit.components.atom

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.strayalpaca.addpokit.model.PokitProfile
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitProfileImage(
    pokitProfile: PokitProfile,
    onClick: (PokitProfile) -> Unit,
    focused: Boolean = false,
) {
    val activeStrokeColor = PokitTheme.colors.brand
    val strokeColor = remember(focused) {
        if (focused) {
            activeStrokeColor
        } else {
            Color.Unspecified
        }
    }

    Image(
        painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_plus_r),
        contentDescription = "pokit profile image",
        modifier = Modifier
            .size(66.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable {
                onClick(pokitProfile)
            }
            .border(
                color = strokeColor,
                width = 1.dp,
                shape = RoundedCornerShape(12.dp)
            )
    )
}
