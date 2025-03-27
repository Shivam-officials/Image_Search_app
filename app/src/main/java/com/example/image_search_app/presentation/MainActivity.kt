package com.example.image_search_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.example.image_search_app.presentation.ui.theme.Image_Search_appTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            var q by rememberSaveable { mutableStateOf("") }
            Image_Search_appTheme {
                Scaffold(
                    topBar = {
                        TextField(
                            singleLine = true,
                            value = q,
                            onValueChange = { value ->
                                q = value
                                viewModel.updateQuery(value)
                            }, modifier = Modifier.fillMaxWidth()
                        )
                    }, modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.statusBars.asPaddingValues())
                ) { innerPadding ->
                    MainContent(
                        viewmodel = viewModel,
                        modifier = Modifier
                            .padding(innerPadding)

                    )
                }
            }
        }
    }
}
//todo what is causing the mainContent flow to recompose bcz no state observering has been change to trigger recompose so what is making it trigger :flow?

@Composable
fun MainContent(viewmodel: MainViewModel, modifier: Modifier = Modifier) {
    val images = viewmodel.images.collectAsLazyPagingItems()

    if (images.loadState.refresh is LoadState.NotLoading) {
        if (images.itemCount == 0) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Nothing found")
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
    ) {


        if (images.loadState.append is LoadState.Loading) {
            item {
//                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
//                }
            }
        }



        if (images.loadState.prepend is LoadState.Loading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }



        if (images.loadState.refresh is LoadState.NotLoading) {
            if (images.itemCount != 0) {
                items(
                    count = images.itemCount,
                    key = images.itemKey { it.uuid },
                    contentType = images.itemContentType { "contenttype" }
                ) { index ->
                    AsyncImage(
                        model = images[index]?.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
        }

        if (images.loadState.append is LoadState.Error) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(onClick = { images.retry() }, modifier = Modifier.fillMaxWidth()) {
                        Text("retry")
                    }
                }
            }
        }



        if (images.loadState.prepend is LoadState.Error) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(onClick = { images.retry() }, modifier = Modifier.fillMaxWidth()) {
                        Text("retry")
                    }
                }
            }
        }


    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Image_Search_appTheme {
//        MainContent(MainViewMo)
    }
}