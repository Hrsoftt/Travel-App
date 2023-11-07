package com.example.gotravels

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.request.ImageRequest.Builder
import com.example.gotravels.data.DestinationsLocalDataSource
import com.example.gotravels.data.DestinationsRepository
import com.example.gotravels.data.ExploreModel
import com.example.gotravels.ui.BottomSheetShape
import com.example.gotravels.ui.travelApp_caption
import com.example.gotravels.ui.travelApp_divider_color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@Composable
fun ExploreSection(
    modifier: Modifier = Modifier,
    title: String,
    exploreList: List<ExploreModel>,
    //onItemClicked: OnExploreItemClicked

) {
    Surface(modifier = modifier.fillMaxSize(), color = Color.White, shape = BottomSheetShape) {
        Column(modifier = Modifier.padding(start = 24.dp, top = 20.dp, end = 24.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.caption.copy(travelApp_caption)
            )
            Box(Modifier.weight(1f)) {
                val listState = rememberLazyListState()
                ExploreList(exploreItems = exploreList, listState)

                val showButton by remember {
                    derivedStateOf {
                        listState.firstVisibleItemIndex > 0
                    }
                }


                if (showButton){
                    val coroutineScope = rememberCoroutineScope()
                    FloatingActionButton(
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .navigationBarsPadding()
                            .padding(bottom = 8.dp),
                        onClick = {
                            coroutineScope.launch {
                                listState.scrollToItem(0)
                            }
                        }
                    ){
                        Text(text = "Up!")
                    }
                }

            }
        }
    }
}

@Composable
fun ExploreList(
    exploreItems: List<ExploreModel>,
    listState: LazyListState
    //onItemClicked: OnExploreItemClicked
) {
    LazyColumn(
        modifier = Modifier,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        state = listState
    ) {
        items(exploreItems) { exploreItem ->
            Column(Modifier.fillParentMaxWidth()) {
                ExploreItem(
                    modifier = Modifier.fillParentMaxWidth(),
                    item = exploreItem,
                    onItemClicked = { /* hollo */ }
                )
                Divider(color = travelApp_divider_color)
            }
        }
    }
}

@Composable
private fun ExploreItem(
    modifier: Modifier = Modifier,
    item: ExploreModel,
    onItemClicked: (ExploreModel) -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(item) }
            .padding(top = 12.dp, bottom = 12.dp)
    ) {
        ExploreImageContainer {
            Box(
            ) {
                val painter = rememberAsyncImagePainter(
                    model = Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (painter.state is AsyncImagePainter.State.Loading) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_crane_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        Spacer(Modifier.width(24.dp))
        Column {
            Text(
                text = item.city.nameToDisplay,
                style = MaterialTheme.typography.h6
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.caption.copy(travelApp_caption)
            )
        }
    }
}


val destinations = DestinationsLocalDataSource.craneDestinations


@Composable
fun ExploreImageContainer(content: @Composable () -> Unit) {
    Surface(Modifier.size(width = 60.dp, height = 60.dp), RoundedCornerShape(4.dp)) {
        content()
    }
}

@Composable
fun Runner(listState: LazyListState) {
//    destinations.forEach { destination ->
//        ExploreItem(
//            item = destination,
//            onItemClicked = { /* */ },
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//    for (destination in destinations){
//        ExploreItem(
//            item = destination,
//            onItemClicked = { /* */ },
//            modifier = Modifier.fillMaxSize()
//        )
//    }

    LazyColumn(
        modifier = Modifier,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        state = listState
    ) {
        items(destinations) { destination ->
            Column(Modifier.fillParentMaxWidth()) {
                ExploreItem(
                    modifier = Modifier.fillParentMaxWidth(),
                    item = destination,
                    onItemClicked = { /* hollo */ }
                )
                Divider(color = Color.Red)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExplorePreview() {
    val listState = rememberLazyListState()
    ExploreSection(title = "Hello", exploreList = destinations )
}