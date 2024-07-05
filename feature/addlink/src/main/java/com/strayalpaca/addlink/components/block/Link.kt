package com.strayalpaca.addlink.components.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun Link(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                color = PokitTheme.colors.borderTertiary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Image(
            painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_google),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(124.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
                maxLines = 2,
                style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.textSecondary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "https://pokit.com/watch?v=xSTwqkUyM8k",
                maxLines = 2,
                style = PokitTheme.typography.detail2.copy(color = PokitTheme.colors.textTertiary)
            )
        }
    }
}
