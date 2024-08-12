import SwiftUI
import shared

struct DetailsScreen: View {
    @ObservedObject var viewModel: NewsViewModel
    var articleUrl: String

    var body: some View {
        VStack(alignment: .leading) {
            if let article = viewModel.selectedArticle {
                Text(article.title )
                    .font(.headline)
                    .padding()

                if let description = article.description {
                    Text(description)
                        .padding()
                }

                Spacer()
            } else {
                Text("Loading...")
                    .padding()
            }
        }
        .navigationBarTitle("KKM News Details Screen", displayMode: .inline)
        .onAppear {
            viewModel.fetchNewsDetails(articleUrl: articleUrl)
        }
    }
}

struct DetailsScreen_Previews: PreviewProvider {
    static var previews: some View {
        DetailsScreen(viewModel: NewsViewModel(repository: NewsRepository()), articleUrl: "")
    }
}
