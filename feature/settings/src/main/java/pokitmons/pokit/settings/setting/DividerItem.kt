package pokitmons.pokit.settings.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun DividerItem() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(
            modifier = Modifier
                .height(6.dp)
                .fillMaxWidth()
                .background(color = PokitTheme.colors.backgroundPrimary)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
