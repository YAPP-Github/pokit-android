package pokitmons.pokit.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.home.pokit.PokitViewModel
import pokitmons.pokit.home.pokit.ScreenType
import pokitmons.pokit.core.ui.R.drawable as DrawableResource

@Composable
fun BottomNavigationBar(viewModel: PokitViewModel = hiltViewModel()) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        shadowElevation = 20.dp
    ) {
        BottomAppBar(
            containerColor = PokitTheme.colors.backgroundBase,
            modifier = Modifier.height(92.dp),
            tonalElevation = 8.dp
        ) {
            BottomNavigationBarButton(
                modifier = Modifier.weight(2f),
                contentDescription = "포킷",
                text = "포킷",
                iconResourceId = DrawableResource.icon_24_folder,
                selected = viewModel.screenType.value == ScreenType.Pokit,
                onClick = { viewModel.updateScreenType(ScreenType.Pokit) }
            )

            BottomNavigationBarButton(
                modifier = Modifier.weight(2f),
                contentDescription = "리마인드",
                text = "리마인드",
                iconResourceId = DrawableResource.icon_24_remind,
                selected = viewModel.screenType.value == ScreenType.Remind,
                onClick = { viewModel.updateScreenType(ScreenType.Remind) }
            )
        }
    }
}

@Composable
private fun BottomNavigationBarButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    contentDescription: String?,
    text: String,
    iconResourceId: Int,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val iconColor = if (selected) Color.Black else PokitTheme.colors.iconTertiary
    val textColor = if (selected) Color.Black else PokitTheme.colors.textTertiary

    Column(
        modifier = modifier
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = interactionSource
            )
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconResourceId),
            contentDescription = contentDescription,
            tint = if (isPressed) PokitTheme.colors.iconDisable else iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            color = if (isPressed) PokitTheme.colors.textDisable else textColor,
            style = PokitTheme.typography.detail2,
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}
