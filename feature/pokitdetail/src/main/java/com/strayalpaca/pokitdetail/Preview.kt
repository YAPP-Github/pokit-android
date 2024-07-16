package com.strayalpaca.pokitdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.strayalpaca.pokitdetail.model.sampleLinkList
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PokitDetailScreen(
                linkList = sampleLinkList
            )
        }
    }
}
