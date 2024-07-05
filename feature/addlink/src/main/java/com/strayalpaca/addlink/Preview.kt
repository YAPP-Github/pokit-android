package com.strayalpaca.addlink

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun AddLinkScreenPreview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(PokitTheme.colors.backgroundBase)
        ) {
            AddLinkScreen()
        }
    }
}
