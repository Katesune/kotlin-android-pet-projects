package com.katesune.filmograf.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.katesune.filmograf.ui.theme.FilmografTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "MoviesActivity"

class MoviesActivity : ComponentActivity() {

    private val moviesViewModel by viewModel<MoviesViewModel>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            FilmografTheme {

                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        MovieSearchMenu(
                            confirmValue = { query ->
                                navController.switchToMovieByQuery(moviesViewModel, query) },
                            searchQuery = moviesViewModel.searchQuery
                        )},
                    content = { innerPadding ->
                        Surface (
                            modifier = Modifier.consumeWindowInsets(innerPadding)
                                .padding(innerPadding)
                        ) {
                            Navigate(
                                navController,
                                moviesViewModel,
                            )
                        }
                    })
            }
        }
    }
}


@Composable
fun Navigate(
    navController: NavHostController,
    moviesViewModel: MoviesViewModel,
) {

    NavHost(
        navController = navController,
        startDestination = Destinations.RANDOM.route
    ) {

        composable(Destinations.RANDOM.route) {
            SingleMovieDataRecompose(
                moviesViewModel = moviesViewModel,
            )
        }

        composable(
            Destinations.ID.route,
            arguments = listOf(navArgument(Destinations.ID.argKey) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(Destinations.ID.argKey)?.let { movieId ->

                MovieByIdRecompose(
                    moviesViewModel = moviesViewModel,
                    movieId
                )

            }
        }

        composable(
            Destinations.SEARCH.route,
            arguments = listOf(navArgument(Destinations.SEARCH.argKey) { type = NavType.StringType })
        ) { backStackEntry ->

            backStackEntry.arguments?.getString(Destinations.SEARCH.argKey)?.let { searchQuery ->

                MoviesDataRecompose(
                    switchToSingleMovie =  { movieId ->
                        navController.switchToMovieById(moviesViewModel, movieId) },
                    moviesViewModel = moviesViewModel
                )

            }
        }

    }
}

private fun NavHostController.switchToMovieById(
    moviesViewModel: MoviesViewModel,
    movieId: Int
) {
    moviesViewModel.collectByMovieId(movieId)
    this.navigate(route = Destinations.ID.basicRoute + "/$movieId")
}

private fun NavHostController.switchToMovieByQuery(
    moviesViewModel: MoviesViewModel,
    query: String
) {
    moviesViewModel.collectByQuery(query)
    this.navigate(route = Destinations.SEARCH.basicRoute + "/$query")
}