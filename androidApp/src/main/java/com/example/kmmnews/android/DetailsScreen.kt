package com.example.kmmnews.android

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(viewModel: NewsViewModel, navController: NavController, articleUrl: String) {

    val decodedUrl = URLDecoder.decode(articleUrl, StandardCharsets.UTF_8.toString())

    LaunchedEffect(decodedUrl) {
        viewModel.fetchNewsDetails(decodedUrl)
    }

    val article by viewModel.selectedNewsArticle.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KMM News Details Screen") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        article?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Image with larger aspect ratio
                it.urlToImage?.let { imageUrl ->
                    Image(
                        painter = rememberImagePainter(data = imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(21f / 9f) // Larger aspect ratio (21:9)
                            .heightIn(min = 180.dp, max = 300.dp), // Ensures the height is within a certain range
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.title ?: "", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.description ?: "No description available.")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.content ?: "No content available.")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Published at: ${it.publishedAt}")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Source: ${it.source.name}")
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
