package pokitmons.pokit.home.pokit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.drawable as DrawableResource

@Composable
fun PokitItem(
    pokitTitle: String,
    linkCount: Int
) {
    Surface(
        modifier = Modifier
            .aspectRatio(1f)
            .height(146.dp)
            .fillMaxSize()
            .background(color = PokitTheme.colors.backgroundTertiary),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = pokitTitle,
                    style = PokitTheme.typography.label2SemiBold
                )

                Icon(
                    painter = painterResource(id = DrawableResource.icon_24_kebab),
                    contentDescription = "케밥",
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                color = PokitTheme.colors.textTertiary,
                text = "링크 ${linkCount}개",
                style = PokitTheme.typography.body3Medium
            )

            // Todo Coil 매핑
            Image(
                modifier = Modifier
                    .align(Alignment.End)
                    .size(84.dp),
                painter = painterResource(id = DrawableResource.icon_logo),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun Test() {
    PokitItem("요리/레시피", 10)
}
