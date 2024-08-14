package com.katesune.cryptoapp.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
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
import coil.compose.rememberAsyncImagePainter
import com.katesune.cryptoapp.R
import com.katesune.cryptoapp.ui.theme.Green
import com.katesune.cryptoapp.ui.theme.LiteGray
import com.katesune.cryptoapp.ui.theme.MediumGray
import com.katesune.cryptoapp.ui.theme.Primary
import com.katesune.cryptoapp.ui.theme.Red
import com.katesune.cryptoapp.ui.theme.Secondary
import com.katesune.cryptoapp.ui.theme.Typography
import com.katesune.domain.models.MarketCoin

private val tinyPadding = 8.dp

private val buttonHeight = 32.dp
private val buttonWidth = 89.dp
private val coinIconSize = 40.dp

private const val TAG = "ScreenDetails"

@Composable
fun ChipCurrencyButton(
    currency: String,
    isChecked: Boolean,
    switchToMarketCoins: (String) -> Unit,
) {

    val backgroundColor = if (isChecked) Secondary.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.2f)
    val textColor = if (isChecked) Primary else Color.Black.copy(alpha = 0.87f)

    Button(
        onClick = { if (!isChecked) switchToMarketCoins(currency) },

        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = Color.Black.copy(alpha = 0.2f),
            disabledContentColor = Color.Black,
        ),

        modifier = Modifier
            .padding(end = tinyPadding)
            .height(buttonHeight)
            .width(buttonWidth),

        content = {
            Text(
                text = currency,
                style = Typography.bodyMedium,
                color = textColor,
            )
        },

        contentPadding = PaddingValues(0.dp)
    )
}

@Composable
fun CoinsCellsRow(
    marketCoins: List<MarketCoin>,
    currency: String,
    switchToCoinById: (String) -> Unit,
) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
    ) {
        items(marketCoins) { marketCoin ->
            CoinCell(
                marketCoin = marketCoin,
                currency = currency,
                switchToCoinById = switchToCoinById,
            )
        }
    }
}

@Composable
fun CoinCell(
    marketCoin: MarketCoin,
    currency: String,
    switchToCoinById: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = tinyPadding)
            .clickable { switchToCoinById(marketCoin.id) },
    ) {

        Row(
            modifier = Modifier.align(Alignment.TopStart),
            horizontalArrangement = Arrangement.spacedBy(tinyPadding),
        ) {

            CoinIcon(
                coinIconUrl = marketCoin.image,
            )

            CoinLabel(
                coinId = marketCoin.id,
                coinName = marketCoin.symbol.uppercase(),
            )
        }

        val coinPriceChange = marketCoin.price_change_24h.roundPercentage()
        val coinCurrentPrice = marketCoin.current_price.changeToCurrentCurrency(currency)

        CoinPrice(
            price = coinCurrentPrice,
            percentage = coinPriceChange,
            modifier = Modifier.align(Alignment.TopEnd),
        )
    }
}

@Composable
private fun Int.changeToCurrentCurrency(currency: String): String {
    return if (currency.contains("USD")) {
        stringResource(id = R.string.usd_symbol) + " " + this.toFloat().toString()
    } else stringResource(id = R.string.rub_symbol) + " " + this.toFloat().toString()
}

private fun Float.roundPercentage(): String {
    val percentage = String.format("%.2f", this)
    return if (this > 0) {
        "+ $percentage"
    }  else percentage.replace("-", "- ")
}

@Composable
fun CoinIcon(
    coinIconUrl: String,
) {
    Column {
        Image(
            painter = if (coinIconUrl.isBlank()) {
                painterResource(R.drawable.error_icon)
            } else rememberAsyncImagePainter(coinIconUrl),
            contentDescription = stringResource(id = R.string.icon_coin_description),
            modifier = Modifier
                .height(coinIconSize)
                .width(coinIconSize)
        )
    }
}

@Composable
fun CoinLabel(
    coinId: String,
    coinName: String,
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = coinId.capitalize(),
            color = MediumGray,
            textAlign = TextAlign.Start,
            style = Typography.labelSmall,
        )

        Text(
            text = coinName.uppercase(),
            color = LiteGray,
            textAlign = TextAlign.Start,
            style = Typography.bodyMedium,
        )

    }
}

@Composable
fun CoinPrice(
    price: String,
    percentage: String,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = price,
            color = MediumGray,
            textAlign = TextAlign.End,
        )

        Text(
            text = percentage,
            color = if (percentage.contains(stringResource(id = R.string.plus_symbol))) Green else Red,
            textAlign = TextAlign.End,
        )

    }
}

@Preview
@Composable
fun MarketCoinsPreview() {
    Surface() {
        CoinsCellsRow(
            listOf(
                MarketCoin(id = "bitcoin", symbol = "Bitcoin", current_price = 5574647, price_change_24h = 2.5f)
            ),
            currency = "UDS",
            {}
        )
    }

}

