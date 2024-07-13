package com.strayalpaca.addpokit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.strayalpaca.addpokit.model.AddPokitScreenState
import com.strayalpaca.addpokit.model.samplePokitList
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun Preview(

) {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AddPokitScreen(
                state = AddPokitScreenState().copy(pokitList = samplePokitList)
            )
        }
    }
}
