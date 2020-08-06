//
//  CocoaExecutor.swift
//  DEV
//
//  Created by Amr Yousef on 06/08/2020.
//  Copyright © 2020 Amr Yousef. All rights reserved.
//

import data

class CocoaExecutor: Executor {
	func executeInBackground(block: @escaping () -> Any?, completion: @escaping (Any?) -> Void) {
		DispatchQueue.global(qos: .background).async {
			let result = block()
			DispatchQueue.main.async {
				completion(result)
			}
		}
	}
}
