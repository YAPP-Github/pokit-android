package pokitmons.pokit.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.login.R
import pokitmons.pokit.core.ui.R.drawable as coreDrawable

@Composable
fun SignUpSuccessScreen(
    onNavigateToMainScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(38.dp))

            Image(
                modifier = Modifier.size(90.dp),
                painter = painterResource(id = coreDrawable.party_popper),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                color = PokitTheme.colors.brand,
                style = PokitTheme.typography.title1,
                text = stringResource(id = R.string.sign_up_success)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                textAlign = TextAlign.Center,
                style = PokitTheme.typography.body1Bold,
                text = stringResource(id = R.string.manage_links)
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier.height(308.dp),
                painter = painterResource(id = coreDrawable.big_pokki),
                contentDescription = "big_pokki"
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        PokitButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.start),
            icon = null,
            size = PokitButtonSize.LARGE,
            onClick = { onNavigateToMainScreen() }
        )
    }
}
