//
//  BackgroundExecutor.swift
//  DEV
//
//  Created by Amr Yousef on 10/08/2020.
//  Copyright © 2020 Amr Yousef. All rights reserved.
//

import data

class BackgroundExecutor: Executor {
	func executeInBackground(block: KotlinSuspendFunction0, completion: @escaping (Any?) -> Void) {
		BackgroundKt.invoke0(block: block, completionHandler: { result, error in
			DispatchQueue.main.async {
				completion(result)
			}
		})
	}
}
