import SwiftUI
import shared

struct ContentView: View {
    @StateObject private var viewModel = NewsViewModel(repository: NewsRepository())

    var body: some View {
        NavigationView {
            NewsScreen(viewModel: viewModel)
                .navigationBarTitle("News", displayMode: .inline)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
