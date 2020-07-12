//
//  ContentView.swift
//  DEV
//
//  Created by Amr Yousef on 04/07/2020.
//  Copyright © 2020 Amr Yousef. All rights reserved.
//

import SwiftUI
import data

struct ContentView: View {
	private let viewModel = ArticlesListViewModel()
	@State var state: ArticlesListState = ArticlesListState.Loading()
    var body: some View {
		StateText(state: state)
			.onAppear {
				viewModel.loadData()
				viewModel.watchState {
					self.state = $0
				}
			}
	}
}

struct StateText: View {
	let state: ArticlesListState
	
	var body: some View {
		switch state {
		case is ArticlesListState.Error:
			return AnyView(Text("Error"))
		case is ArticlesListState.Loading:
			return AnyView(ProgressView())
		case is ArticlesListState.Ready:
			return AnyView(ArticlesList(items: (state as! ArticlesListState.Ready).articles ))
		default:
			return AnyView(ProgressView())
		}
	}
}

struct ArticlesList: View {
	let items: [ArticlesListStateItem]
	
	var body: some View {
		NavigationView {
			List(self.items, id: \.name) {
				ArticleRow(item: $0)
			}
			.navigationTitle("Articles")
		}
	}
}

struct ArticleRow: View {
	let item: ArticlesListStateItem
	var body: some View {
		VStack(alignment: .leading, spacing: 8) {
			Text(item.name)
				.font(.title)
			Text(item.author)
				.font(.caption)
		}
	}
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
		ArticleRow(item: ArticlesListStateItem(name: "Test Article name", author: "Amr Yousef"))
    }
}
