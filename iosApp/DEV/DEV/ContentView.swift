//
//  ContentView.swift
//  DEV
//
//  Created by Amr Yousef on 04/07/2020.
//  Copyright © 2020 Amr Yousef. All rights reserved.
//

import SwiftUI
import presentation

class ArticlesListStateWrapper: ObservableObject {
	@Published var state: ArticlesListState
	let viewModel: CFArticlesListViewModel
	
	init(viewModel: CFArticlesListViewModel) {
		self.viewModel = viewModel
		state = ArticlesListState.Loading()
	}
	
	func startObserving() {
		viewModel.onChanged { recievedState in
			self.state = recievedState
		}
	}
	
	func stopObserving() {
		viewModel.cancel()
	}
}

struct ContentView: View {
	private let wrapper = ArticlesListStateWrapper(viewModel: CFArticlesListViewModel())
	var body: some View {
		StateText()
			.onAppear {
				wrapper.startObserving()
			}.onDisappear {
				wrapper.stopObserving()
			}.environmentObject(wrapper)
	}
}

struct StateText: View {
	@EnvironmentObject
	var wrapper: ArticlesListStateWrapper
	
	init() {}
	
	var body: some View {
		switch wrapper.state {
		case is ArticlesListState.Error:
			return AnyView(Text("Error"))
		case is ArticlesListState.Loading:
			return AnyView(ProgressView())
		case is ArticlesListState.Ready:
			return AnyView(ArticlesList(items: (wrapper.state as! ArticlesListState.Ready).data, viewModel: wrapper.viewModel))
		default:
			return AnyView(ProgressView())
		}
	}
}

struct IdentifiableReadyArticleState : Identifiable, Equatable {
	let article: ArticlesListState.ReadyArticleState
	let id = UUID()
	
	
}

struct ArticlesList: View {
	let items: [IdentifiableReadyArticleState]
	let viewModel: CFArticlesListViewModel
	
	init(items: [ArticlesListState.ReadyArticleState], viewModel: CFArticlesListViewModel) {
		self.items = items.map{
			IdentifiableReadyArticleState(article: $0)
		}
		self.viewModel = viewModel
	}
	
	var body: some View {
		NavigationView {
			ScrollView(showsIndicators: true) {
				LazyVStack {
					ForEach(self.items) { article  in
						NavigationLink(destination: Text("Hamada")
										.navigationBarTitleDisplayMode(.inline)
						) {
							ArticleRow(item: article.article)
								.frame(minHeight: 210)
								.shadow(radius: 1)
								.onAppear {
									if items.firstIndex(of: article) == items.count - 10 {
										viewModel.onLoadMore()
									}
								}
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
