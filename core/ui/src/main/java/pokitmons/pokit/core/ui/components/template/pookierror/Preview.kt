package pokitmons.pokit.core.ui.components.template.pookierror

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PokitTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ErrorPooki(title = "오류가 발생했어요", sub = "조금 뒤 다시 접속해주세요", onClickRetry = null)
            // ErrorPooki(title = "오류가 발생했어요", sub = "조금 뒤 다시 접속해주세요", onClickRetry = {})
        }
    }
}
