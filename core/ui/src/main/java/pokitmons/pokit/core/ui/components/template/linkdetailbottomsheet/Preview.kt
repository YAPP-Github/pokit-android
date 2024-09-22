package pokitmons.pokit.core.ui.components.template.linkdetailbottomsheet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun LinkDetailBottomSheetPreview() {
    PokitTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LinkDetailBottomSheet(
                title = "title",
                memo = "some memo",
                url = "https://naver.com",
                thumbnailPainter = painterResource(id = R.drawable.icon_24_google),
                bookmark = true,
                openWebBrowserByClick = false,
                show = true,
                pokitName = "TEXT",
                dateString = "2024.08.27",
                onHideBottomSheet = { },
                onClickBookmark = { },
                onClickModifyLink = { },
                onClickRemoveLink = { },
                onClickShareLink = { }
            )
        }
    }
}
