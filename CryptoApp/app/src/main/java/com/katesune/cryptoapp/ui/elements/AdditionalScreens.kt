package com.katesune.cryptoapp.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.katesune.cryptoapp.R
import com.katesune.cryptoapp.ui.theme.Primary
import com.katesune.cryptoapp.ui.theme.Typography

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column{
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize(0.1f)
                    .align(Alignment.CenterHorizontally),
                color = Primary
            )
        }
    }
}

private val middlePadding = 13.dp
private val largePadding = 30.dp
private val errorIconSize = 120.dp

@Composable
fun ErrorScreen(
    tryAgain: (String) -> Unit,
    navigationArgument: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        ErrorLoadingIcon()
        ErrorLoadingLabel()
        ErrorLoadingButton(
            tryAgain = tryAgain,
            navigationArgument = navigationArgument,
        )
    }
}

@Composable
fun ErrorLoadingIcon() {
    Row {
        Image(
            painter = painterResource(R.drawable.error_icon),
            contentDescription = stringResource(id = R.string.icon_coin_description),
            modifier = Modifier
                .size(errorIconSize)
                .padding(bottom = middlePadding)
        )

    }
}

@Composable
fun ErrorLoadingLabel() {
    Row(
        modifier = Modifier
            .padding(bottom = largePadding)
    ) {
        Text(
            text = stringResource(id = R.string.error_message),
            textAlign = TextAlign.Center,
            style = Typography.titleMedium,
        )
    }
}

@Composable
fun ErrorLoadingButton(
    tryAgain: (String) -> Unit,
    navigationArgument: String,
) {
    Button(
        onClick = { tryAgain(navigationArgument) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = Color.White,
            disabledContainerColor = Color.Black.copy(alpha = 0.2f),
            disabledContentColor = Color.Black,
        ),
        content = {
            Text(
                text = stringResource(id = R.string.try_again_button),
                style = Typography.bodyMedium
            )
        },
        shape = RectangleShape
    )
}