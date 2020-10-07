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
			}.navigationTitle("Articles").background(Color(hex: "#edf0f1"))
		}.navigationViewStyle(StackNavigationViewStyle())
	}
}

extension Color {
	init(hex: String) {
		let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
		var int: UInt64 = 0
		Scanner(string: hex).scanHexInt64(&int)
		let a, r, g, b: UInt64
		switch hex.count {
		case 3: // RGB (12-bit)
			(a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
		case 6: // RGB (24-bit)
			(a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
		case 8: // ARGB (32-bit)
			(a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
		default:
			(a, r, g, b) = (1, 1, 1, 0)
		}

		self.init(
			.sRGB,
			red: Double(r) / 255,
			green: Double(g) / 255,
			blue:  Double(b) / 255,
			opacity: Double(a) / 255
		)
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
