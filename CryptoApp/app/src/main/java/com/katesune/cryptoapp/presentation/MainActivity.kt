package com.katesune.cryptoapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.katesune.cryptoapp.ui.theme.CryptoAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {

    private val coinsViewModel by viewModel<CoinsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CryptoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text(
                        text = "firstCoin",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Navigate(
    navController: NavHostController,
    coinsViewModel: CoinsViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.MARKET_COINS.route
    ) {
        composable(
            Destinations.MARKET_COINS.route,
            arguments = listOf(navArgument(Destinations.MARKET_COINS.argKey) { type = NavType.StringType }),
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Destinations.MARKET_COINS.argKey)?.let { currency ->

                MarketCoinsScreen(
                    coinsViewModel = coinsViewModel,
                    currency = currency,
                    switchToMarketCoins =  { currentCurrency ->
                        navController.switchToMarketCoins(coinsViewModel, currentCurrency) },
                )

            } ?:  MarketCoinsScreen(
                    coinsViewModel = coinsViewModel,
                    currency = "usd",
                    switchToMarketCoins =  { currentCurrency ->
                        navController.switchToMarketCoins(coinsViewModel, currentCurrency) },
                )
        }

        composable(
            Destinations.COIN.route,
            arguments = listOf(navArgument(Destinations.COIN.argKey) { type = NavType.StringType }),
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Destinations.COIN.argKey)?.let { coinId ->

//                CoinsScreen(
//                    coinsViewModel = coinsViewModel,
//                    currency = currency
//                )

            }
        }
    }
}

private fun NavHostController.switchToMarketCoins(
    coinsViewModel: CoinsViewModel,
    currency: String,
) {
    coinsViewModel.collectMarketCoins(currency)
    this.navigate(route = Destinations.MARKET_COINS.basicRoute + "/$currency")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoAppTheme {
       // Greeting("Android")
    }
}