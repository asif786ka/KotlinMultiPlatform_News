package com.example.kmmnews.android

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.kmmnews.NewsArticle

@Composable
fun NewsScreen(
    navController: NavController = rememberNavController(),
    viewModel: NewsViewModel
) {
    val articles = viewModel.newsArticles.collectAsState()
    val loading = viewModel.loading.collectAsState()

    // Fetch news when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.fetchNews(query = "technology", fromDate = "2024-07-12") // Example parameters
    }

    if (loading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(articles.value) { article ->
                NewsCard(article) {
                    val encodedUrl = java.net.URLEncoder.encode(article.url, "UTF-8")
                    navController.navigate("details/$encodedUrl")
                }
            }
        }
    }
}

@Composable
fun NewsCard(article: NewsArticle, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Column {
            article.urlToImage?.let { imageUrl ->
                Image(
                    painter = rememberImagePainter(data = imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f) // Aspect ratio of 16:9
                        .heightIn(min = 180.dp, max = 240.dp), // Ensures the height is within a certain range
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = article.title ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = article.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
