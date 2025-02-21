package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ArtSpaceLayout()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceLayout() {

    val artworkImageResources = getArtworkImageResources()

    val artworkTitleResources = getArtworkTitleResources()

    val artworkArtistResources = getArtworkArtistResources()

    val artworkYearResources = getArtworkYearResources()

    /** notice the state is hoisted by design - past learning **/

    var portraitIndex by remember { mutableIntStateOf(0) }

    println("in the layout the index is $portraitIndex")

    val portraitImage = artworkImageResources[portraitIndex]

    val portraitContentDescription = artworkTitleResources[portraitIndex]

    val portraitTitle = artworkTitleResources[portraitIndex]

    val portraitArtist = artworkArtistResources[portraitIndex]

    val portraitYear = artworkYearResources[portraitIndex]

    val portraitCount = artworkImageResources.size

    /** start UI here **/

    ArtSpaceOuterColumn(
        image = portraitImage,
        contentDescription = portraitContentDescription,
        title = portraitTitle,
        artist = portraitArtist,
        year = portraitYear,
        previousPortrait = {
            var temp = portraitIndex
            temp--
            if (temp < 0) temp = portraitCount - 1
            portraitIndex = temp
        },
        nextPortrait = {
            var temp = portraitIndex
            temp++
            if (temp >= portraitCount) temp = 0
            portraitIndex = temp
        }
    )

}

@Composable
fun ArtSpaceOuterColumn(
    @DrawableRes image: Int,
    @StringRes contentDescription: Int,
    @StringRes title: Int,
    @StringRes artist: Int,
    @StringRes year: Int,
    previousPortrait: () -> Unit,
    nextPortrait: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Surface(
            modifier = Modifier.padding(dimensionResource(R.dimen.portrait_surface_padding)),
            color = Color.Transparent,
            shadowElevation = dimensionResource(R.dimen.portrait_elevation),
        ) {
            Image(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.portrait_width))
                    .height(dimensionResource(R.dimen.portrait_height))
                    .padding(dimensionResource(R.dimen.portrait_interior_padding)),
                painter = painterResource(image),
                contentScale = ContentScale.Fit,
                contentDescription = stringResource(contentDescription)
            )
        }

        Text(
            text = stringResource(title),
            modifier = Modifier,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        val artistAndYearFormat = stringResource(R.string.portrait_artist_and_year)
        val artistAndYear =
            String.format(artistAndYearFormat, stringResource(artist), stringResource(year))
        Text(
            text = artistAndYear,
            modifier = Modifier,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = dimensionResource(R.dimen.button_area_bottom_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.button_row_padding),
                        end = dimensionResource(R.dimen.button_row_padding)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier,
                    onClick = previousPortrait
                ) {
                    Text(
                        stringResource(R.string.navigate_previous_portrait),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(dimensionResource(R.dimen.minimum_button_width))
                    )
                }
                Button(
                    modifier = Modifier,
                    onClick = nextPortrait
                ) {
                    Text(
                        stringResource(R.string.navigate_next_portrait),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(dimensionResource(R.dimen.minimum_button_width))
                    )
                }
            }
        }
        
    }

}

fun getArtworkImageResources(): Array<Int> {

    val drawableIds = arrayOf(
        R.drawable.jobs_portrait,
        R.drawable.seneca_portrait,
        R.drawable.columbus_portrait,
        R.drawable.van_gogh_portrait,
        R.drawable.aristotle_portrait
    )

    return drawableIds
}

fun getArtworkTitleResources(): Array<Int> {

    val titles = arrayOf(
        R.string.steve_jobs_portrait_title,
        R.string.seneca_portrait_title,
        R.string.columbus_portrait_title,
        R.string.van_gogh_portrait_title,
        R.string.aristotle_portrait_title
    )

    return titles
}

fun getArtworkArtistResources(): Array<Int> {

    val titles = arrayOf(
        R.string.steve_jobs_portrait_artist,
        R.string.seneca_portrait_artist,
        R.string.columbus_portrait_artist,
        R.string.van_gogh_portrait_artist,
        R.string.aristotle_portrait_artist
    )

    return titles
}

fun getArtworkYearResources(): Array<Int> {

    val titles = arrayOf(
        R.string.steve_jobs_portrait_year,
        R.string.seneca_portrait_year,
        R.string.columbus_portrait_year,
        R.string.van_gogh_portrait_year,
        R.string.aristotle_portrait_year
    )

    return titles
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceLayoutPreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}