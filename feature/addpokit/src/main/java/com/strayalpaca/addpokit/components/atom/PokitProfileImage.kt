package com.strayalpaca.addpokit.components.atom

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.strayalpaca.addpokit.model.PokitImage
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable

@Composable
fun PokitProfileImage(
    pokitImage: PokitImage,
    onClick: (PokitImage) -> Unit,
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

    AsyncImage(
        model = pokitImage.url,
        contentDescription = "pokit profile image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(shape = RoundedCornerShape(12.dp))
            .noRippleClickable {
                onClick(pokitImage)
            }
            .border(
                color = strokeColor,
                width = 2.dp,
                shape = RoundedCornerShape(12.dp)
            )
    )
}
