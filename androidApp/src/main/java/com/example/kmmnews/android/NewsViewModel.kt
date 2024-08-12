package com.example.kmmnews.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmmnews.NewsArticle
import com.example.shared.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    // StateFlow for news articles
    private val _newsArticles = MutableStateFlow<List<NewsArticle>>(emptyList())
    val newsArticles: StateFlow<List<NewsArticle>> = _newsArticles

    // StateFlow for selected news article
    private val _selectedNewsArticle = MutableStateFlow<NewsArticle?>(null)
    val selectedNewsArticle: StateFlow<NewsArticle?> = _selectedNewsArticle

    // StateFlow for loading state
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    // Function to fetch news articles
    fun fetchNews(query: String, fromDate: String) {
        viewModelScope.launch {
            _loading.value = true // Set loading to true when fetching starts
            try {
                _newsArticles.value = newsRepository.fetchNews(query, fromDate) ?: emptyList()
            } finally {
                _loading.value = false // Set loading to false when fetching ends
            }
        }
    }

    // Function to fetch details of a specific news article
    fun fetchNewsDetails(articleUrl: String) {
        _selectedNewsArticle.value = _newsArticles.value.find { it.url == articleUrl }
    }
}
