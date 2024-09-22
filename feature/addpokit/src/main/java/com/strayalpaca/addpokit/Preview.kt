package com.strayalpaca.addpokit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.strayalpaca.addpokit.model.AddPokitScreenState
import com.strayalpaca.addpokit.model.Pokit
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AddPokitScreen(
                state = AddPokitScreenState(),
                pokits = samplePokitList
            )
        }
    }
}

private val samplePokitList = listOf(
    Pokit(title = "안드로이드", id = "1", count = 2),
    Pokit(title = "IOS", id = "2", count = 2),
    Pokit(title = "디자인", id = "3", count = 2),
    Pokit(title = "PM", id = "4", count = 1),
    Pokit(title = "서버", id = "5", count = 2)
)
