package com.katesune.cryptoapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import coil.compose.rememberAsyncImagePainter
import com.katesune.cryptoapp.R
import com.katesune.cryptoapp.ui.elements.ErrorScreen
import com.katesune.cryptoapp.ui.elements.LoadingScreen
import com.katesune.cryptoapp.ui.theme.Typography
import com.katesune.domain.models.Coin

private const val TAG = "CoinScreen"

private val shadowElevation = 2.dp
private val middlePadding = 16.dp
private val tinyPadding = 8.dp

private val iconBackSize = 24.dp
private val largeCoinIconSize = 90.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinScreen(
    coinsViewModel: CoinsViewModel,
    switchToMarketCoins: () -> Unit,
    updateMarketCoins: (String) -> Unit,
) {
    CoinLayout(
        coin = coinsViewModel.coin.collectAsState(),
        switchToMarketCoins = switchToMarketCoins,
        coinsViewModel.loadCoinsState.value,
        updateMarketCoins = updateMarketCoins
    )
}

@ExperimentalMaterial3Api
@Composable
fun CoinLayout(
    coin: State<Coin>,
    switchToMarketCoins: () -> Unit,
    loadCoinsState: RequestState,
    updateMarketCoins: (String) -> Unit,
) {

    Scaffold(
        content = { innerPadding ->
            Surface (
                modifier = Modifier
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .consumeWindowInsets(innerPadding)
                        .padding(innerPadding),
                ) {

                    CoinTopAppBAr(
                        id = coin.value.id,
                        switchToMarketCoins = switchToMarketCoins,
                    )

                    when (loadCoinsState) {
                        RequestState.CANCELED -> ErrorScreen(updateMarketCoins, coin.value.id)
                        RequestState.INPROGRESS -> {
                            CoinContent(
                                coin.value,
                                modifier = Modifier.padding(horizontal = middlePadding),
                            )
                        }
                        else -> LoadingScreen()
                    }

                }
            }
        },
    )
}

@Composable
fun CoinTopAppBAr(
    id: String,
    switchToMarketCoins: () -> Unit,
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max)
    ) {
        IconButton(onClick = switchToMarketCoins) {
            Image(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = stringResource(id = R.string.icon_back_description),
                modifier = Modifier
                    .size(iconBackSize)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = id.capitalize(),
                color = Color.Black,
                style = Typography.titleLarge,
                textAlign = TextAlign.Start
            )
        }

    }
}

@ExperimentalMaterial3Api
@Composable
fun CoinTopAppBar(
    id: String,
    switchToMarketCoins: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier
            .shadow(
                elevation = shadowElevation,
                spotColor = Color.Black.copy(alpha = 0.14f)
            ),
        navigationIcon = {
            IconButton(onClick = switchToMarketCoins) {
                Image(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = stringResource(id = R.string.icon_back_description),
                    modifier = Modifier
                        .size(iconBackSize)
                )
            }
        },
        title = {
            Column (
                modifier = Modifier
                    .padding(vertical = middlePadding)
            ) {
                Text(
                    text = id.capitalize(),
                    color = Color.Black,
                    style = Typography.titleLarge,
                )
            }
        },
    )
}

@Composable
fun CoinContent(
    coin: Coin,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

        LargeCoinIcon(
            coinIconUrl = coin.image
        )

        CoinDescription(
            description = coin.description,
        )

        CoinCategories(
            categories = coin.categories
        )
    }
}

@Composable
fun LargeCoinIcon(
    coinIconUrl: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = if (coinIconUrl.isBlank()) {
                painterResource(R.drawable.error_icon)
            } else rememberAsyncImagePainter(coinIconUrl),
            contentDescription = stringResource(id = R.string.icon_coin_description),
            modifier = Modifier
                .height(largeCoinIconSize)
                .width(largeCoinIconSize)
                .padding(top = tinyPadding, bottom = middlePadding)
        )
    }
}

@Composable
fun CoinDescription(
    description: String,
) {
    Row {
        Text(
            text = stringResource(id = R.string.description),
            textAlign = TextAlign.Start,
            style = Typography.titleLarge,
            modifier = Modifier
                .padding(bottom = tinyPadding)
        )
    }

    Row {
        Text(
            text = description,
            textAlign = TextAlign.Start,
            style = Typography.titleMedium,
            modifier = Modifier
                .padding(bottom = middlePadding)
        )
    }
}

@Composable
fun CoinCategories(
    categories: List<String>
) {
    Row {
        Text(
            text = stringResource(id = R.string.categories),
            textAlign = TextAlign.Start,
            style = Typography.titleLarge,
            modifier = Modifier
                .padding(bottom = tinyPadding)
        )
    }
    Row {
        Text(
            text = categories.joinToString(" "),
            textAlign = TextAlign.Start,
            style = Typography.titleMedium,
            modifier = Modifier
                .padding(bottom = middlePadding)
        )
    }
}

@Preview
@Composable
fun CoinScreenPreview() {
    CoinContent(
        Coin(""),
        Modifier
    )
}


