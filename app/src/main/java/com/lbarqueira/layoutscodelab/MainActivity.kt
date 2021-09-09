package com.lbarqueira.layoutscodelab

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.lbarqueira.layoutscodelab.ui.theme.LayoutsCodelabTheme
import kotlinx.coroutines.launch


private const val TAG = "MyActivity"

@ExperimentalCoilApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsCodelabTheme {
                LayoutsCodelab()
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodeLab")
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Phone, contentDescription = null)
                    }
                }
            )
        }
    ) {
        ScrollingList(
            modifier = Modifier
                .padding(it)
                .padding(start = 8.dp, end = 8.dp)
        )
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Hi there!")
        Text(text = "Thanks for going through the Layouts codelab")
    }
}


@Composable
fun SimpleList(modifier: Modifier = Modifier) {
    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState: ScrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {
        repeat(times = 100) {
            Text(text = "Item $it")
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ScrollingList(modifier: Modifier = Modifier) {

    val listSize = 100

    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState: LazyListState = rememberLazyListState()
    Log.i(TAG, "scrollSate is ${scrollState.firstVisibleItemIndex}")

    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier.padding(top=1.dp)) {
        Row {
            Button(
                modifier=Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch {
                        // 0 is the first item index
                        scrollState.animateScrollToItem(0)
                    }
                }
            ) {
                Text("Scroll to the top")

            }
            Spacer(modifier = Modifier.width(2.dp))
            Button(
                modifier=Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch {
                        // listSize - 1 is the last index of the list
                        scrollState.animateScrollToItem(listSize - 1)
                    }
                }
            ) {
                Text("Scroll to the end")
            }
        }

        LazyColumn(state = scrollState) {
            items(
                count = listSize,
                itemContent = {
                    ImageListItem(index = it)
                }
            )
        }
    }

}

@ExperimentalCoilApi
@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png"),
            contentDescription = "Android Logo",
            modifier = Modifier.size(size = 50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
// If you're creating your own composable, consider having a modifier as a parameter,
// default it to Modifier (i.e. empty modifier that doesn't do anything) and apply it
// to the root composable of your function.
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.surface)
            .clickable(onClick = {/* */ })
            .padding(all = 16.dp)
    ) {
        // while the picture is loading, you might want to show a placeholder. For that,
        // you can use a Surface where we specify a circle shape and the placeholder color.
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            // Image goes here
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(alignment = Alignment.CenterVertically)

        ) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }

}


@Preview
@Composable
fun PhotographerCardPreview() {
    LayoutsCodelabTheme {
        PhotographerCard()
    }
}

// note: problem while including the device Nexus_6 preview
// it generates 2 AppBars
@ExperimentalCoilApi
@Preview
@Composable
fun LayoutsCodelabPreview() {
    LayoutsCodelabTheme {
        LayoutsCodelab()
    }
}
