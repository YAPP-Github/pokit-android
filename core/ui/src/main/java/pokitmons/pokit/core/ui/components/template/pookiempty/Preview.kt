package pokitmons.pokit.core.ui.components.template.pookiempty

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
            EmptyPooki(title = "저장된 포킷이 없어요!", sub = "포킷을 생성해 링크를 저장해보세요")
        }
    }
}
