package pokitmons.pokit.core.ui.components.template.removeItemBottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonShape
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.attributes.RemoveItemType
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun RemoveItemBottomSheet(
    removeItemType: RemoveItemType,
    onHideBottomSheet: () -> Unit,
    onClickCancel: () -> Unit,
    onClickRemove: () -> Unit,
) {
    PokitBottomSheet(onHideBottomSheet = onHideBottomSheet) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = removeItemType.titleStringResourceId),
                style = PokitTheme.typography.title2.copy(color = PokitTheme.colors.textPrimary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = removeItemType.subStringResourceId),
                style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary),
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 28.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PokitButton(
                text = stringResource(id = R.string.cancellation),
                icon = null,
                onClick = onClickCancel,
                shape = PokitButtonShape.RECTANGLE,
                type = PokitButtonType.SECONDARY,
                size = PokitButtonSize.LARGE,
                style = PokitButtonStyle.STROKE,
                modifier = Modifier.weight(1f)
            )

            PokitButton(
                text = stringResource(id = R.string.removal),
                icon = null,
                onClick = onClickRemove,
                shape = PokitButtonShape.RECTANGLE,
                type = PokitButtonType.PRIMARY,
                size = PokitButtonSize.LARGE,
                style = PokitButtonStyle.FILLED,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
