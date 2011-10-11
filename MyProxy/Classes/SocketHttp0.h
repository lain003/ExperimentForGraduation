//
//  SocketHttp0.h
//  MyProxy
//
//  Created by akitani on 10/08/31.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>



@interface SocketHttp0 : NSObject {
	int clientDescriptorH;
	int webDescriptorH;
	char clientDataH[2048];
	char webData[2048];
	char host[100];
}

- (id)initWithSocket:(int) socketDiscri;
- (void)runThread;
- (void)webToClientThread;
- (void)clientToWebThread;
- (void)setWebDescriptorAndConnect;
-(void)changeConnecitonHeader;
-(void)changeGetHeader;
-(int)byteSearchString:(int)start
			 searchEnd:(int)end
			searchWord:(char[])word
			wordLength:(int)wordLength;
-(void)byteShift:(int)start
	 shiftLength:(int)length;
-(int)byteSearch:(int)start
	   searchEnd:(int)end
	  searchWord:(char)word;
-(void)runProxy;
-(void)clientToProxy;
-(void)proxyConnect;

@end
