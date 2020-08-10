//
//  BackgroundExecutor.m
//  DEV
//
//  Created by Amr Yousef on 10/08/2020.
//  Copyright © 2020 Amr Yousef. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BackgroundExecutor.h"

@implementation BackgroundExecutor

- (void)executeInBackgroundBlock:(nonnull id<DataKotlinSuspendFunction0>)block completion:(nonnull void (^)(id _Nullable))completion {
	__weak __typeof(block) weakSelf = block;
	[block invokeWithCompletionHandler:^(id _Nullable result, NSError * _Nullable error) {
		completion(result);
		
	}];
//	dispatch_sync(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
//		[block invokeWithCompletionHandler:^(id _Nullable result, NSError * _Nullable error) {
//			completion(result);
//
//		}];
//	});
}

@end
