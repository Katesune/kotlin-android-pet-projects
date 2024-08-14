package com.katesune.cryptoapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.katesune.cryptoapp.R
import com.katesune.cryptoapp.ui.elements.ChipCurrencyButton
import com.katesune.cryptoapp.ui.elements.CoinsCellsRow
import com.katesune.cryptoapp.ui.elements.ErrorScreen
import com.katesune.cryptoapp.ui.elements.LoadingScreen
import com.katesune.cryptoapp.ui.theme.Typography
import com.katesune.domain.models.MarketCoin

private val middlePadding = 16.dp
private val tinyPadding = 8.dp
private val shadowElevation = 2.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketCoinsScreen(
    coinsViewModel: CoinsViewModel,
    currency: String,
    switchToMarketCoins: (String) -> Unit,
    switchToCoinById: (String) -> Unit,
    updateMarketCoins: (String) -> Unit,
) {
    MarketCoinsLayout(
        marketCoins = coinsViewModel.coins.collectAsState(),
        currency = currency,
        switchToMarketCoins = switchToMarketCoins,
        switchToCoinById = switchToCoinById,
        loadCoinsState = coinsViewModel.loadCoinsState.value,
        updateMarketCoins = updateMarketCoins,
    )
}

@ExperimentalMaterial3Api
@Composable
fun MarketCoinsLayout(
    marketCoins: State<List<MarketCoin>>,
    currency: String,
    switchToMarketCoins: (String) -> Unit,
    switchToCoinById: (String) -> Unit,
    loadCoinsState: RequestState,
    updateMarketCoins: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.padding(horizontal = middlePadding),

        content = { innerPadding ->
            Surface (
                modifier = Modifier
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding),
            ) {
                Column (
                    verticalArrangement = Arrangement.spacedBy(middlePadding),
                    modifier = Modifier
                        .consumeWindowInsets(innerPadding)
                        .padding(innerPadding),
                ) {

                    MarketCoinsTopAppBar(
                        currency = currency,
                        switchToMarketCoins = switchToMarketCoins,
                    )

                    when (loadCoinsState) {
                        RequestState.CANCELED -> ErrorScreen(updateMarketCoins, currency)
                        RequestState.INPROGRESS -> {
                            CoinsCellsRow(
                                marketCoins = marketCoins.value,
                                currency = currency,
                                switchToCoinById = switchToCoinById,
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
fun MarketCoinsTopAppBar(
    currency: String,
    switchToMarketCoins: (String) -> Unit,
) {
    Text(
        text = stringResource(id = R.string.crypto_list),
        color = Color.Black,
        style = Typography.titleLarge,
    )

    CurrencyButtons(
        currency = currency,
        switchToMarketCoins = switchToMarketCoins,
    )
}

@Composable
fun CurrencyButtons(
    currency: String,
    switchToMarketCoins: (String) -> Unit,
) {
    Row {
        ChipCurrencyButton(
            currency = stringResource(id = R.string.currency_usd),
            isChecked = (currency.contains(stringResource(id = R.string.currency_usd))),
            switchToMarketCoins = switchToMarketCoins,
        )
        ChipCurrencyButton(
            currency = stringResource(id = R.string.currency_rub),
            isChecked = (currency.contains(stringResource(id = R.string.currency_rub))),
            switchToMarketCoins = switchToMarketCoins,
        )
    }
}