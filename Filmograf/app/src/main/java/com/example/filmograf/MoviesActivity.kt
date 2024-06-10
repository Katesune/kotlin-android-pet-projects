package com.example.filmograf

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.filmograf.ui.theme.FilmografTheme
import com.example.filmograf.ui.theme.Typography
import com.example.filmograf.ui.theme.quote

private const val TAG = "MoviesActivity"

class MoviesActivity : ComponentActivity() {

    private lateinit var moviesViewModel: MoviesViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        setContent {
            FilmografTheme {
                MainContent(moviesViewModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

@Composable
fun MainContent(moviesViewModel: MoviesViewModel) {
    val itemsState = moviesViewModel.moviesLiveData.observeAsState()

    itemsState.value?.let {
        FillMoviesContent(it)
        Log.d(TAG, it.size.toString())
    }
}

@Composable
fun FillMoviesContent(films: List<Movie>) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(films.size) {position ->
            MovieItem(films[position])
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {

        FilmHeader(movie)
        Column (
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            MovieAnnouncement(movie)
            MovieGenres(movie.genres)
            MovieDescription(movie.description)
            MovieSlogan(movie.slogan)
            PersonsRow(movie.persons)
        }
    }
}

@Composable
fun FilmHeader(movie: Movie) {
    Box (
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MoviePoster(movie.title)
        MovieRating(movie.rating, Modifier.align(Alignment.BottomStart))
    }
}

@Composable
fun MoviePoster(title: String) {
    Image (
        painter = painterResource(id = R.drawable.orig),
        contentDescription = stringResource(R.string.poster_description, title),
        contentScale = ContentScale.FillWidth,
        alignment = Alignment.TopStart,

        modifier = Modifier
            //.clip(CutCornerShape(24.dp))
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun MovieRating(rating: Float, modifier: Modifier) {
    Row (
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .height(intrinsicSize = IntrinsicSize.Max)
    ){
        Image (
            painter = painterResource(id = R.drawable.raiting_star),
            contentDescription = stringResource(R.string.star_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )

        Text(
            rating.toString(),
            color = Color.White,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun MovieAnnouncement(movie: Movie) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        MovieTitle(movie.title)
        MovieYear(movie.year, Modifier.align(Alignment.TopEnd))
    }
}

@Composable
fun MovieTitle(title: String) {
    Text(title, style = Typography.titleLarge)
}

@Composable
fun MovieYear(year: String, modifier: Modifier) {
    Text(text = year,
        modifier = modifier
    )
}

@Composable
fun MovieGenres(genres: List<String>) {
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
                .horizontalScroll(rememberScrollState(genres.size / 2))
        ) {
            repeat(genres.size) {
                Text(
                    text = genres[it],
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
}

@Composable
fun MovieDescription(description: String) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            description,
            style = Typography.bodyLarge.copy(
                lineBreak = LineBreak.Paragraph,
                hyphens = Hyphens.Auto
            ),
            textAlign = TextAlign.Justify,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
    }
}

@Composable
fun MovieSlogan(slogan: String) {
    Column (
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth()

    ){
        Text(
            text = slogan,
            style = Typography.quote,
            textAlign = TextAlign.Center,
            //modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun PersonsRow(persons: List<Person>) {
    Row (
        modifier = Modifier
            .horizontalScroll(rememberScrollState()) // this makes it scrollable
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(bottom = 8.dp)
    ) {
        persons.forEach { person ->
            MoviePerson(person)
        }
    }
}

@Composable
fun MoviePerson(person: Person) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(end = 16.dp)
            .width(intrinsicSize = IntrinsicSize.Min)
    ) {
        Image (
            painter = painterResource(id = R.drawable.person),
            contentDescription = stringResource(R.string.person_description),
            contentScale = ContentScale.Fit,
            )

        Row {
            Text(
                person.name,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    FilmografTheme {
        FillMoviesContent(listOf(
            Movie(
                title = "new new film",
                rating = 4.36f,
                year = "1995",
                genres = listOf("Криминал", "Ужасы", "Короткометражка", "Триллер", "Комедия", "Драма"),
                slogan = "\"Можно пережить все - даже психологию\"",
                persons = listOf(Person(name = "Harry Potter"), Person(name = "Liana Rojer")),
                description = "В центре сюжета — психолог, специализирующийся на событиях, которые пресса квалифицирует как «чудо». Реально ли этим явлениям нет объяснения или их все-таки можно научно обосновать? Компанию скептически настроенной девушке составят священник и мутноватого вида «синий воротничок». Они сошлись, лед и пламень, то есть холодное научное знание и мистика — чета Кинг предлагает включиться в очередную увлекательную борьбу противоположностей.")
        ))
    }
}