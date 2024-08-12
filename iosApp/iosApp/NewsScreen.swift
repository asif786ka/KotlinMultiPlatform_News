import SwiftUI
import shared

struct NewsScreen: View {
    @ObservedObject var viewModel: NewsViewModel

    var body: some View {
        List(viewModel.articles, id: \.url) { article in
            NavigationLink(destination: DetailsScreen(viewModel: viewModel, articleUrl: article.url)) {
                Text(article.title )
            }
        }
        .onAppear {
            viewModel.fetchNews(query: "technology", fromDate: "2024-07-12")
        }
        .navigationBarTitle("News", displayMode: .inline)
    }
}

struct NewsScreen_Previews: PreviewProvider {
    static var previews: some View {
        NewsScreen(viewModel: NewsViewModel(repository: NewsRepository()))
    }
}
