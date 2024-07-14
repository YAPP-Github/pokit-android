package com.strayalpaca.pokitdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.strayalpaca.pokitdetail.model.Pokit
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
                currentPokit = Pokit(title = "포킷명", id = "1", count = 10),
                linkList = sampleLinkList
            )
        }
    }
}
