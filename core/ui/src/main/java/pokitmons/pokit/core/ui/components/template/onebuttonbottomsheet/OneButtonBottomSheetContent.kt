package pokitmons.pokit.core.ui.components.template.onebuttonbottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun OneButtonBottomSheetContent(
    title: String,
    sub: String? = null,
    buttonText: String = stringResource(id = R.string.confirmation),
    onClickButton: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(36.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = title,
            textAlign = TextAlign.Center,
            style = PokitTheme.typography.title2.copy(color = PokitTheme.colors.textPrimary)
        )

        sub?.let { subText ->
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = subText,
                textAlign = TextAlign.Center,
                style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 28.dp)
        ) {
            PokitButton(
                text = buttonText,
                icon = null,
                onClick = onClickButton,
                shape = PokitButtonShape.RECTANGLE,
                type = PokitButtonType.PRIMARY,
                size = PokitButtonSize.LARGE,
                style = PokitButtonStyle.FILLED,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
