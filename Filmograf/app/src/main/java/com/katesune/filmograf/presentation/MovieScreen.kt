package com.katesune.filmograf.presentation

import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.katesune.filmograf.R
import com.katesune.filmograf.domain.models.Movie
import com.katesune.filmograf.domain.models.Person
import com.katesune.filmograf.ui.theme.FilmografTheme
import com.katesune.filmograf.ui.theme.Typography
import com.katesune.filmograf.ui.theme.quote
import kotlin.math.roundToInt

private val tinyPadding = 8.dp
private val middlePadding = 16.dp
private val largePadding = 24.dp

private val TAG = "MovieScreen"

@Composable
fun MoviesRow(
    switchToSingleMovie: (Int) -> Unit,
    movies: List<Movie>,
    pictures: List<Bitmap?>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = tinyPadding)
    )  {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
        ) {
            MovieCell(
                switchToSingleMovie,
                movies[0],
                pictures[0],
            )
        }
        movies.getOrNull(1)?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {
                MovieCell(
                    switchToSingleMovie,
                    movies[1],
                    pictures[1],
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MovieCellPreview() {
    FilmografTheme {
        ScreenWithMovies(
            {},
            listOf( Movie(
                title = "new new film",
                rating = 4.36f,
                year = "1995",
                genres = listOf("Криминал", "Ужасы"),
                slogan = "\"Можно пережить все - даже психологию\"",
                persons = listOf(Person(name = "Harry Potter"), Person(name = "Liana Rojer")),
                description = "В центре сюжета — психолог, специализирующийся на событиях, " +
                        "втереться в доверие к Торетто, подозреваемому в причастности " +
                        "к дерзким грабежам грузовиков, совершаемым прямо на ходу.",
                shortDescription = "События в Harry Potter"),
                Movie(
                    title = "new new film",
                    rating = 4.36f,
                    year = "1995",
                    genres = listOf("Комедия", "Драма"),
                    slogan = "\"Можно пережить все - даже психологию\"",
                    persons = listOf(Person(name = "Harry Potter"), Person(name = "Liana Rojer")),
                    description = "специализирующийся на событиях",
                    shortDescription = "События в Harry Potter"),
                Movie(
                    title = "new new film",
                    rating = 4.36f,
                    year = "1995",
                    genres = listOf("Комедия", "Драма"),
                    slogan = "\"Можно пережить все - даже психологию\"",
                    persons = listOf(Person(name = "Harry Potter"), Person(name = "Liana Rojer")),
                    description = "специализирующийся на событиях",
                    shortDescription = "События в Harry Potter"),),
            
            mapOf("fwwd" to null)
        )
    }
}

@Composable
fun MovieCell(
    switchToSingleMovie: (Int) -> Unit,
    movie: Movie,
    poster: Bitmap?,
) {
    val paint = if (poster != null) BitmapPainter(poster.asImageBitmap())
    else painterResource(id = R.drawable.empty_image)

    Column (
        Modifier
            .padding(vertical = tinyPadding)
    ) {
        MovieCellImage(switchToSingleMovie,movie.id, paint)
        MovieCellTitle(switchToSingleMovie, movie.id, movie.title)
        if (movie.shortDescription.isBlank()) MovieCellDescription(movie.description)
        else MovieCellDescription(movie.shortDescription)
    }
}

@Composable
fun MovieCellImage(
    switchToSingleMovie: (Int) -> Unit,
    movieId: Int,
    paint: Painter,
) {
    Image (
        painter = paint,
        contentDescription = stringResource(R.string.poster_description),
        contentScale = ContentScale.FillWidth,
        alignment = Alignment.TopStart,

        modifier = Modifier
            .clickable { switchToSingleMovie(movieId) }
            .padding(bottom = tinyPadding)
    )
}

@Composable
fun MovieCellTitle(
    switchToSingleMovie: (Int) -> Unit,
    movieId: Int,
    title: String,
) {
    Text(
        title,
        style = Typography.bodyLarge,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clickable {
                switchToSingleMovie(movieId)
            }
            .fillMaxWidth()
            .padding(horizontal = tinyPadding, vertical = tinyPadding)
    )
}

@Composable
fun MovieCellDescription(
    description: String,
) {
    Text(
        description,
        textAlign = TextAlign.Justify,
        style = Typography.bodyLarge,
        fontSize = 14.sp,
        maxLines = 5,
        modifier = Modifier
            .padding(horizontal = tinyPadding)

    )
}


@Composable
fun MovieWithDetails(
    movie: Movie,
    pictures: Map<String, Bitmap?>
) {
    val poster = pictures.getOrDefault(movie.posterUri, null)

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                //.weight(2f)
        ) {
            MovieHeader(movie, poster)
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = largePadding)
                .padding(bottom = tinyPadding)
                .weight(4f)
        ) {
            MovieBodyWithDetails(movie, pictures)
        }
    }
}



@Composable
fun SingleBasicMovie(
    movie: Movie,
    poster: Bitmap?,
    modifier: Modifier
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        MovieHeader(movie, poster)
        MovieBody(movie)
    }
}

@Composable
fun MovieHeader(
    movie: Movie,
    poster: Bitmap?
) {
    Box (
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MoviePoster(movie.title, poster)
        MovieRating(movie.rating, Modifier.align(Alignment.BottomStart))
    }
}

@Composable
fun MoviePoster(
    movieTitle: String,
    poster: Bitmap?
) {
    val paint = if (poster != null) BitmapPainter(poster.asImageBitmap())
    else painterResource(id = R.drawable.empty_image)

    val configuration = LocalConfiguration.current
    val posterHeight = remember (poster) {
        poster?.let {
            calculatePosterHeight(it, configuration).dp
        } ?: 200.dp
    }

    Image (
        painter = paint,
        contentDescription = stringResource(R.string.poster_description, movieTitle),
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopStart,

        modifier = Modifier
            .fillMaxWidth()
            .height(posterHeight)
    )
}

fun calculatePosterHeight(poster: Bitmap, configuration: Configuration): Int {
    var posterHeight = (poster.asImageBitmap().height *
            ( configuration.screenWidthDp.dp / poster.asImageBitmap().width.dp))
        .toInt()

    if (posterHeight > configuration.screenHeightDp / 2) posterHeight = configuration.screenHeightDp / 2

    return posterHeight
}

@Composable
fun MovieRating(rating: Float, modifier: Modifier) {
    Row (
        modifier = modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .padding(start = middlePadding)
            .background(
                color = Color.White,
                shape = RectangleShape
            )
            .padding(
                start = tinyPadding,
                bottom = tinyPadding,
                top = tinyPadding,
                end = middlePadding
            )
    ){
        Image (
            painter = painterResource(id = R.drawable.raiting_star),
            contentDescription = stringResource(R.string.star_description),
            contentScale = ContentScale.Crop,

            modifier = Modifier
                .align(Alignment.Top)
                .padding(end = 2.dp)
                .fillMaxHeight()
        )

        Text(
            rating.toString(),
            color = Color.Black,
            style = Typography.bodyLarge,
            textAlign = TextAlign.Start,
            letterSpacing = 0.sp,
            modifier = Modifier
                .align(Alignment.Bottom)
        )
    }
}

@Composable
fun MovieBody(
    movie: Movie
) {
    Column (
        modifier = Modifier
            .padding(horizontal = largePadding)
            .padding(bottom = tinyPadding)
    ) {
        MovieAnnouncement(movie.title, movie.year)
        MovieGenres(movie.genres)
        MovieDescription(movie.description)
    }
}

@Composable
fun MovieBodyWithDetails(
    movie: Movie,
    pictures: Map<String, Bitmap?>,
) {
    MovieAnnouncement(movie.title, movie.year)
    MovieGenres(movie.genres)
    MovieDescription(movie.description)
    MovieSlogan(movie.slogan)
    PersonsRow(movie.persons, pictures)
}

@Composable
fun MovieAnnouncement(
    movieTitle: String,
    movieYear: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = tinyPadding)
    ) {
        MovieYear(
            movieYear,
            Modifier
                .padding(start = middlePadding)
                .fillMaxWidth()
        )
        MovieTitle(
            movieTitle,
            Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun MovieTitle(
    title: String, modifier: Modifier
) {
    Text(
        title,
        style = Typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun MovieYear(year: String, modifier: Modifier) {
    Text(
        text = year,
        modifier = modifier,
        textAlign = TextAlign.End,
    )
}

@Composable
fun MovieGenres(
    genres: List<String>,
    ) {
    Row(
        modifier = Modifier
            .padding(top = tinyPadding, bottom = middlePadding)
            .horizontalScroll(rememberScrollState(genres.size / 2))
    ) {
        repeat(genres.size) {
            Text(
                text = genres[it],
                modifier = Modifier.padding(end = tinyPadding)
            )
        }
    }
}

@Composable
fun MovieDescription(
    description: String,
) {
    Text(
        description,
        style = Typography.bodyLarge.copy(
            lineBreak = LineBreak.Paragraph,
            hyphens = Hyphens.Auto
        ),
        textAlign = TextAlign.Justify,
    )
}

@Composable
fun MovieSlogan(slogan: String) {
    Text(
        text = slogan,
        style = Typography.quote,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(
                top = tinyPadding,
                bottom = tinyPadding,
                start = middlePadding,
                end = middlePadding
            )
            .fillMaxWidth()
    )
}

@Composable
fun PersonsRow(
    persons: List<Person>,
    pictures: Map<String, Bitmap?>
) {
    Row (
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .width(intrinsicSize = IntrinsicSize.Min)
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(bottom = middlePadding)
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
    else painterResource(id = R.drawable.empty_image)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(tinyPadding),
        modifier = Modifier
            .padding(end = tinyPadding)
            .fillMaxHeight()
    ) {
        Image (
            painter = painter,
            contentDescription = stringResource(R.string.person_description),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(100.dp)
        )
        Row (
            modifier = Modifier
                .width(intrinsicSize = IntrinsicSize.Min)
                .height(intrinsicSize = IntrinsicSize.Max)
        ) {
            Text(
                person.name,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieWithDetailsPreview() {
    FilmografTheme {
        MovieWithDetails(
            Movie(
                title = "new new film",
                rating = 4.36f,
                year = "1995",
                genres = listOf("Криминал", "Ужасы", "Короткометражка", "Триллер", "Комедия", "Драма"),
                slogan = "\"Можно пережить все - даже психологию\"",
                persons = listOf(Person(name = "Harry Potter"), Person(name = "Liana Rojer")),
                description = "В центре сюжета — психолог, специализирующийся на событиях, которые пресса квалифицирует как «чудо». Реально ли этим явлениям нет объяснения или их все-таки можно научно обосновать? Компанию скептически настроенной девушке составят священник и мутноватого вида «синий воротничок». Они сошлись, лед и пламень, то есть холодное научное знание и мистика — чета Кинг предлагает включиться в очередную увлекательную борьбу противоположностей."),
        mapOf()
        )
    }
}