package pokitmons.pokit.core.ui.components.block.linkurlcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun LinkUrlCardPreview() {
    PokitTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LinkUrlCard(
                    thumbnailPainter = painterResource(id = R.drawable.icon_24_google),
                    url = "https://naver.com",
                    title = "네이버",
                    openWebBrowserByClick = false
                )

                LinkUrlCard(
                    modifier = Modifier.padding(20.dp),
                    thumbnailPainter = painterResource(id = R.drawable.icon_24_google),
                    url = "https://naver.com",
                    title = "네이버",
                    openWebBrowserByClick = false
                )
            }
        }
    }
}
