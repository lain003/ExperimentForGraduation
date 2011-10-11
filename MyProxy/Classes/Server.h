//
//  Server.h
//  MyProxy
//
//  Created by akitani on 10/08/22.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Server : NSObject {
int clientDescriptor;
}

-(void)run;
-(void)passThread;

@end