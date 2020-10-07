//
//  AsyncImage.swift
//  DEV
//
//  Created by Amr Yousef on 06/10/2020.
//  Copyright © 2020 Amr Yousef. All rights reserved.
//
import SwiftUI

struct AsyncImage<Placeholder: View>: View {
	@StateObject private var loader: ImageLoader
	private let placeholder: Placeholder
	private let image: (UIImage) -> Image
	private let hieght: CGFloat?
	private let width: CGFloat?
	
	init(
		url: URL,
		width: CGFloat? = nil,
		hieght: CGFloat? = nil,
		@ViewBuilder placeholder: () -> Placeholder,
		@ViewBuilder image: @escaping (UIImage) -> Image = Image.init(uiImage:)
	) {
		self.placeholder = placeholder()
		self.image = image
		self.width = width
		self.hieght = hieght
		_loader = StateObject(wrappedValue: ImageLoader(url: url, cache: Environment(\.imageCache).wrappedValue))
	}
	
	var body: some View {
		content
			.onAppear(perform: loader.load)
	}
	
	private var content: some View {
		VStack {
			if loader.image != nil {
				image(loader.image!)
			} else {
				placeholder
			}
		}.frame(width: width, height:hieght)
	}
}
