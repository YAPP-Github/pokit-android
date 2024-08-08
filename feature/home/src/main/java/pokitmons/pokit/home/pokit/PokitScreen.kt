package pokitmons.pokit.home.pokit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIcon
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIconPosition
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonShape
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.drawable as DrawableResource

@Composable
fun PokitScreen() {
    Spacer(modifier = Modifier.height(8.dp))
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PokitButton(
                text = "포킷",
                shape = PokitButtonShape.ROUND,
                icon = PokitButtonIcon(
                    DrawableResource.icon_24_folderline,
                    PokitButtonIconPosition.LEFT
                ),
                onClick = { /*TODO*/ }
            )

            Spacer(modifier = Modifier.padding(start = 12.dp))

            PokitButton(
                text = "포킷",
                shape = PokitButtonShape.ROUND,
                icon = PokitButtonIcon(
                    DrawableResource.icon_24_info,
                    PokitButtonIconPosition.LEFT
                ),
                type = PokitButtonType.SECONDARY,
                style = PokitButtonStyle.STROKE,
                onClick = { /*TODO*/ }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(
                    painter = painterResource(id = DrawableResource.icon_24_align),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.padding(start = 2.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "최신순",
                    style = PokitTheme.typography.body3Medium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomRow() {
    PokitScreen()
}
