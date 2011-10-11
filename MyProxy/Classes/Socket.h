//
//  Socket.h
//  MyProxy
//
//  Created by akitani on 10/08/23.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Socket : NSObject {
	int clientDescriptor;
	int webDescriptor;
	char clientData[2048];
	char webData[2048];
	char host[100];	
}
- (id)initWithSocket:(int) socketDiscri;
- (void)runThread;
- (void)clientToWebThread;
- (void)changeGetHeader;
- (void)changeConnecitonHeader;
- (int)byteSearch:(int)start
	    searchEnd:(int)end
	   searchWord:(char)word;
-(void)byteShift:(int)start
	 shiftLength:(int)length;

-(int)byteSearchString:(int)start
			 searchEnd:(int)end
			searchWord:(char[])word
			wordLength:(int)wordLength;
-(void)sendClientDeta;
- (void)setWebDescriptorAndConnect;
- (void)webToClientThread;
-(void)proxyConnect;


@end
