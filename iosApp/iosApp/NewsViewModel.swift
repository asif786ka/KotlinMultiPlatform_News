import SwiftUI
import shared

class NewsViewModel: ObservableObject {
    // Published properties to update the UI
    @Published var articles: [NewsArticle] = []
    @Published var selectedArticle: NewsArticle?
    @Published var isLoading: Bool = false

    private let repository: NewsRepository

    // Initialize with the shared repository
    init(repository: NewsRepository) {
        self.repository = repository
        fetchNews() // Optionally fetch news when the view model is initialized
    }

    // Function to fetch news articles
    func fetchNews(query: String = "technology", fromDate: String = "2024-07-12") {
        isLoading = true
        repository.fetchNews(query: query, fromDate: fromDate) { [weak self] news, error in
            DispatchQueue.main.async {
                if let news = news {
                    self?.articles = news
                } else if let error = error {
                    print("Error fetching news: \(error)")
                    self?.articles = []
                }
                self?.isLoading = false
            }
        }
    }

    // Function to fetch the details of a specific news article
    func fetchNewsDetails(articleUrl: String) {
        selectedArticle = articles.first { $0.url == articleUrl }
    }
}

