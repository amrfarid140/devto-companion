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
		DispatchQueue.global(qos: .background).async {
			let articles = ApiService().getArticles()
			print(articles)
		}
//		ApiService().getArticles { articles in
//			print(articles)
//		}
	}
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
