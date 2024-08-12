package com.example.kmmnews.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shared.NewsRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsRepository = NewsRepository()
        val viewModel = NewsViewModel(newsRepository)

        setContent {
            MyApp(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(viewModel: NewsViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()


    // Determine if we are on the details screen
    val isDetailsScreen = navBackStackEntry?.destination?.route?.startsWith("details") == true

    Scaffold(
        topBar = {
            if (!isDetailsScreen) {
                TopAppBar(
                    title = { Text("KMM News List Screen") },
                    // Any other top bar content here
                )
            }
        },
        content = {
            NavHost(navController = navController, startDestination = "newsList") {
                composable("newsList") {
                    NewsScreen(navController = navController, viewModel = viewModel)
                }
                composable("details/{newsUrl}") { backStackEntry ->
                    val newsUrl = backStackEntry.arguments?.getString("newsUrl") ?: ""
                    DetailsScreen(viewModel = viewModel, navController = navController, articleUrl = newsUrl)
                }
            }
        }
    )
}
