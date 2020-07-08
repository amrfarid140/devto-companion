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
    var body: some View {
        Text("Hello, World!")
	
		.onAppear {
			self.test()
		}
	}
	
	func test() {
		KtorArticlesApi(apiKey: "BNJUyn8jhYZoiihKgnwiT7fW").getArticles { (articles:[Article]?, error: Error?) in
			print(articles)
		}
	}
//		DispatchQueue.global(qos: .background).async {
//			let articles = ApiService().getArticles()
//
//		}
//		ApiService().getArticles { articles in
//			print(articles)
//		}
//	}
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
