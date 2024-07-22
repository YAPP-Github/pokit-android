package pokitmons.pokit.search.components.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.model.Date
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.samplePokits

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilterArea()

            FilterArea(
                filter = Filter(
                    selectedPokits = samplePokits
                )
            )

            FilterArea(
                filter = Filter(
                    startDate = Date(year = 2024, month = 7, day = 20),
                    endDate = Date(year = 2024, month = 7, day = 20),
                )
            )

            FilterArea(
                filter = Filter(
                    bookmark = true
                )
            )
        }
    }
}
