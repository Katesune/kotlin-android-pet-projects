package com.example.filmograf.presentation

import android.graphics.Bitmap
import android.os.Bundle
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.filmograf.MoviesViewModel
import com.example.filmograf.R
import com.example.filmograf.domain.models.Movie
import com.example.filmograf.domain.models.Person
import com.example.filmograf.ui.theme.FilmografTheme
import com.example.filmograf.ui.theme.Typography
import com.example.filmograf.ui.theme.quote
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "MoviesActivity"

class MoviesActivity : ComponentActivity() {

    private val moviesViewModel by viewModel<MoviesViewModel>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FilmografTheme {
                moviesViewModel.collectMovies()
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
    val movies = moviesViewModel.movies.collectAsState()
    val pictures = moviesViewModel.imageCollection.collectAsState()

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(movies.value.size) {position ->
            MovieItem(movies.value[position], pictures.value)
        }
    }

}


@Composable
fun MovieItem(movie: Movie, pictures: Map<String, Bitmap?>) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {

        val poster = pictures.getOrDefault(movie.posterUri, null)
        FilmHeader(movie, poster)

        Column (
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            MovieAnnouncement(movie)
            MovieGenres(movie.genres)
            MovieDescription(movie.description)
            MovieSlogan(movie.slogan)
            PersonsRow(movie.persons, pictures)
        }
    }
}

@Composable
fun FilmHeader(movie: Movie, poster: Bitmap?) {
    Box (
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MoviePoster(movie, poster)
        MovieRating(movie.rating, Modifier.align(Alignment.BottomStart))
    }
}

@Composable
fun MoviePoster(movie: Movie, poster: Bitmap?) {

    val paint = if (poster != null) BitmapPainter(poster.asImageBitmap())
    else painterResource(id = R.drawable.orig)

    Image (
        painter = paint,
        contentDescription = stringResource(R.string.poster_description, movie.title),
        contentScale = ContentScale.FillWidth,
        alignment = Alignment.TopStart,

        modifier = Modifier
            //.clip(CutCornerShape(24.dp))
            .fillMaxWidth()
            .height(250.dp)

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
fun PersonsRow(persons: List<Person>, pictures: Map<String, Bitmap?>) {
    Row (
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(bottom = 8.dp)
    ) {
        persons.forEach { person ->
            val personPhoto = pictures.getOrDefault(person.photoUrl, null)
            MoviePerson(person, personPhoto)
        }
    }
}

@Composable
fun MoviePerson(person: Person, photo: Bitmap?) {

    val painter = if (photo != null) BitmapPainter(photo.asImageBitmap())
    else painterResource(id = R.drawable.person)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(end = 16.dp)
            .width(intrinsicSize = IntrinsicSize.Min)
            .height(intrinsicSize = IntrinsicSize.Max)
    ) {
        Image (
            painter = painter,
            contentDescription = stringResource(R.string.person_description),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
            )

        Row {
            Text(
                person.name,
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview2() {
//    FilmografTheme {
//        FillMoviesContent(listOf(
//            Movie(
//                title = "new new film",
//                rating = 4.36f,
//                year = "1995",
//                genres = listOf("Криминал", "Ужасы", "Короткометражка", "Триллер", "Комедия", "Драма"),
//                slogan = "\"Можно пережить все - даже психологию\"",
//                persons = listOf(Person(name = "Harry Potter"), Person(name = "Liana Rojer")),
//                description = "В центре сюжета — психолог, специализирующийся на событиях, которые пресса квалифицирует как «чудо». Реально ли этим явлениям нет объяснения или их все-таки можно научно обосновать? Компанию скептически настроенной девушке составят священник и мутноватого вида «синий воротничок». Они сошлись, лед и пламень, то есть холодное научное знание и мистика — чета Кинг предлагает включиться в очередную увлекательную борьбу противоположностей.")
//        ))
//    }
//}