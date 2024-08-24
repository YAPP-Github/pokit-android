package pokitmons.pokit.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pokitmons.pokit.LoginViewModel
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.login.R

@Composable
fun SignUpSuccessScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onNavigateToMainScreen: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 28.dp)
    ) {
        Icon(
            modifier = Modifier.padding(start = 4.dp),
            painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_arrow_left),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(38.dp))

            Image(
                modifier = Modifier.size(90.dp),
                painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.sign_up_icon),
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
        }

        PokitButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = stringResource(id = R.string.start),
            icon = null,
            size = PokitButtonSize.LARGE,
            onClick = { onNavigateToMainScreen() }
        )
    }
}

@Preview
@Composable
fun SignUpSuccessScreenPreview() {
    SignUpSuccessScreen(onNavigateToMainScreen = { })
}
