//
//file://localhost/Users/akitani/Documents/MyProxy//  MyProxyAppDelegate.h
//  MyProxy
//
//  Created by akitani on ß10/08/22.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MyProxyAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
	
	IBOutlet UILabel* labe1;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
- (IBAction)sayHello;
@end

