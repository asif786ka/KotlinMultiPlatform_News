package com.example.kmmnews

import kotlinx.serialization.Serializable

@Serializable
data class NewsArticle(
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val source: NewsSource
)

@Serializable
data class NewsSource(
    val id: String?,
    val name: String
)

@Serializable
data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticle>
)
