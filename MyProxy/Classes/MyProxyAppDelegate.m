//
//  MyProxyAppDelegate.m
//  MyProxy
//
//  Created by akitani on 10/08/22.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "MyProxyAppDelegate.h"
#import "Server.h"
#import "Socket.h"

#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
#include <netdb.h>
#include <netinet/in.h>
#include <sys/param.h>
#include <sys/uio.h>
#include <unistd.h>

@implementation MyProxyAppDelegate

@synthesize window;


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {    

    // Override point for customization after application launch
	
    [window makeKeyAndVisible];
	
	return YES;
}


- (void)dealloc {
    [window release];
    [super dealloc];
}
/*- (void)ontime
{
	printf("gaaa\n");
}*/

- (void)hread
{
	while (TRUE) {
		[NSThread exit];
	
	}
}
- (IBAction)sayHello
{
	/*printf("Program Star\n\n\n");
	
	id server = [Server alloc];
	[server run];*/
	
	/*[NSThread detachNewThreadSelector:@selector(hread) toTarget:self withObject:[NSNumber numberWithInt:1]];
	*/
	/*NSTimer* timer;
	timer = [NSTimer scheduledTimerWithTimeInterval:1
											 target:self // このselfはretainされる
										   selector:@selector(ontime)
										   userInfo:nil
											repeats:NO];
	printf("hell\n");*/
	//sleep(100);
	//[timer invalidate];
	//[timer release];
	
	char aa[100] = "hekko";
	NSString* s = [[NSString alloc]initWithBytes:aa length:sizeof(aa) encoding:NSUTF8StringEncoding];
	NSData* data = [NSData dataWithBytes:aa length:5];
	NSString* s2 = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
	char *a3;
	a3 = [s2 UTF8String];
	printf("%s",a3);
	
	/*struct sockaddr_in server;
	int sock;
	char buf[32];
	char *deststr;
	unsigned int **addrptr;
	
	
	deststr = "www.google.co.jp";
	
	sock = socket(AF_INET, SOCK_STREAM, 0);
	if (sock < 0) {
		perror("socket");
		return 1;
	}
	
	server.sin_family = AF_INET;
	server.sin_port = htons(80);
	server.sin_len = sizeof(server);
	
	server.sin_addr.s_addr = inet_addr(deststr);
	if (server.sin_addr.s_addr == 0xffffffff) {
		struct hostent *host;
		
		host = gethostbyname(deststr);
		if (host == NULL) {
			if (h_errno == HOST_NOT_FOUND) {
			
				printf("host not found : %s\n", deststr);
			} else {
	
				printf("%s : %s\n", hstrerror(h_errno), deststr);
			}
			return 1;
		}
		
		addrptr = (unsigned int **)host->h_addr_list;
		
		while (*addrptr != NULL) {
			server.sin_addr.s_addr = *(*addrptr);
			
			if (connect(sock,
						(struct sockaddr *)&server,
						sizeof(server)) == 0) {
				break;
			}
			
			addrptr++;
			
		}
		
		
		if (*addrptr == NULL) {
			perror("connect");
		}
	} else {
		if (connect(sock,
					(struct sockaddr *)&server, sizeof(server)) != 0) {
			perror("connect");
		}
	}
	
	
	memset(buf, 0, sizeof(buf));
	snprintf(buf, sizeof(buf), "GET / HTTP/1.0\r\n\r\n");
	
	
	int n = write(sock, buf, (int)strlen(buf));
	if (n < 0) {
		perror("write");
	}
	
	while (n > 0) 
	{
		memset(buf, 0, sizeof(buf));
		n = read(sock, buf, sizeof(buf));
		if (n < 0) {
			perror("read");
		}
		write(1, buf, n);
		printf("%d\n",n);
	}
	//printf("%s",buf);
	printf("quit");
	
	close(sock);*/
	
}


@end
