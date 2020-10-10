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
	
	init(items: [ArticlesListState.ReadyArticleState]) {
		self.items = items
	}
	
	var body: some View {
		NavigationView {
			ScrollView(showsIndicators: true) {
				LazyVStack {
					ForEach(self.items, id: \.title) { article  in
						NavigationLink(destination: Text("Hamada")
										.navigationBarTitleDisplayMode(.inline)
						) {
							ArticleRow(item: article)
								.frame(minHeight: 210)
								.shadow(radius: 1)
						}
					}
				}
				.padding(EdgeInsets(top: 12, leading: 12, bottom: 0, trailing: 12))
			}.navigationTitle("Articles")
			.background(Color(hex: "#edf0f1"))
		}.navigationViewStyle(StackNavigationViewStyle())
	}
}

struct ArticleRow: View {
	let item: ArticlesListState.ReadyArticleState
	var body: some View {
		GeometryReader { geo in
			VStack(alignment: .leading, spacing: 8) {
				AsyncImage(
					url: URL(string: item.coverImageUrl)!,
					width: geo.size.width,
					hieght: 140,
					placeholder: { AnyView(ProgressView()) },
					image: {
						Image(uiImage: $0)
							.resizable()
					}
				)
				VStack {
					Text(item.title)
						.lineLimit(nil)
						.font(Font.title2.weight(.bold))
						.foregroundColor(Color.black)
						.frame(maxWidth: .infinity, alignment: .leading)
					Text(item.userName)
						.font(.subheadline)
						.foregroundColor(Color.black)
						.frame(maxWidth: .infinity, alignment: .leading)
				}.padding(EdgeInsets(top: 0, leading: 8, bottom: 12, trailing: 8))
			}
		}
		.background(Color.white)
		.cornerRadius(6)
	}
}
