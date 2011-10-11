//
//  Server.m
//  MyProxy
//
//  Created by akitani on 10/08/22.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "Server.h"
#import "Socket.h"
#import "SocketHttp0.h"
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

@implementation Server

int clientDescriptor;

- (void)run
{
	int sockDescriptor;
	
	struct sockaddr_in addr;
	struct sockaddr_in client;
	
	sockDescriptor = socket(AF_INET, SOCK_STREAM, 0);
	
	addr.sin_family = AF_INET;
	addr.sin_port = htons(8081);
	addr.sin_addr.s_addr = INADDR_ANY;
	addr.sin_len = sizeof(addr);
	
	bind(sockDescriptor, (struct sockaddr *)&addr, sizeof(addr));
	
	listen(sockDescriptor, 5);
	printf("Listen Start\n");
	while(true)
	{
		int len = sizeof(client);
		clientDescriptor = accept(sockDescriptor, (struct sockaddr *)&client, &len);
		printf("-------Socket Accept--------\n");
		
		//[NSThread detachNewThreadSelector:@selector(passThread) toTarget:self withObject:[NSNumber numberWithInt:1]];
		id clientSocket = [[SocketHttp0 alloc] initWithSocket:clientDescriptor];
		[clientSocket runThread];
		/*id clientSocket = [[SocketHttp0 alloc] initWithSocket:clientDescriptor];
		[clientSocket runProxy];*/
	}
}
- (void) passThread
{
	/*id clientSocket = [[Socket alloc] initWithSocket:clientDescriptor];
	[clientSocket runThread];*/
	id clientSocket = [[SocketHttp0 alloc] initWithSocket:clientDescriptor];
	[clientSocket runThread];
	/*id clientSocket = [[SocketHttp0 alloc] initWithSocket:clientDescriptor];
	 [clientSocket runProxy];*/
}
@end


