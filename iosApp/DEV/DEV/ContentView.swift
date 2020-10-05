//
//  ContentView.swift
//  DEV
//
//  Created by Amr Yousef on 04/07/2020.
//  Copyright © 2020 Amr Yousef. All rights reserved.
//

import SwiftUI
import presentation

struct ContentView: View {
	private let viewModel = CFArticlesListViewModel()
	@State var state: ArticlesListState = ArticlesListState.Loading()
	var body: some View {
		StateText(state: state)
			.onAppear {
				viewModel.onChanged { recievedState in
					self.state = recievedState
				}
			}.onDisappear {
				viewModel.cancel()
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
			return AnyView(ArticlesList(items: (state as! ArticlesListState.Ready).data ))
		default:
			return AnyView(ProgressView())
		}
	}
}

struct ArticlesList: View {
	let items: [ArticlesListState.ReadyArticleState]
	
	var body: some View {
		NavigationView {
			List(self.items, id: \.title) {
				ArticleRow(item: $0)
			}
			.navigationTitle("Articles")
		}
	}
}

struct ArticleRow: View {
	let item: ArticlesListState.ReadyArticleState
	var body: some View {
		VStack(alignment: .leading, spacing: 8) {
			Text(item.title)
				.font(.title)
			Text(item.userName)
				.font(.caption)
		}
	}
}
