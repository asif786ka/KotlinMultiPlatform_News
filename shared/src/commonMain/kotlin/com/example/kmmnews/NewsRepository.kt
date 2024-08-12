package com.example.shared

import co.touchlab.kermit.Logger
import com.example.kmmnews.NewsApiResponse
import com.example.kmmnews.NewsArticle
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class NewsRepository {
    private val client = HttpClient()
    private val json = Json { ignoreUnknownKeys = true }
    private val logger = Logger.withTag("NewsRepository")
    private val apiKey = "7a8818963ecf40f6b5e5791a3d699320"  // Replace with your actual API key

    suspend fun fetchNews(query: String, fromDate: String): List<NewsArticle>? {
        return try {
            val response: HttpResponse = client.get("https://newsapi.org/v2/everything") {
                parameter("q", query)
                parameter("from", fromDate)
                parameter("sortBy", "publishedAt")
                parameter("apiKey", apiKey)
            }
            val rawResponse = response.bodyAsText()
            logger.d { "Raw response: $rawResponse" }

            if (rawResponse.isBlank()) {
                logger.e { "Received an empty response from the API" }
                null
            } else {
                val newsResponse = json.decodeFromString<NewsApiResponse>(rawResponse)
                logger.d { "Parsed response: $newsResponse" }
                newsResponse.articles
            }
        } catch (e: Exception) {
            logger.e(e) { "Error parsing JSON response" }
            null
        }
    }
}


