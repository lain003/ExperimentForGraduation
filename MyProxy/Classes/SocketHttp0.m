//
//  SocketHttp0.m
//  MyProxy
//
//  Created by akitani on 10/08/31.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

//
//  Socket.m
//  MyProxy
//
//  Created by akitani on 10/08/23.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "SocketHttp0.h"
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

@implementation SocketHttp0

int clientDescriptorH;
int webDescriptorH = 0;
char clientDataH[2048];
char webData[2048];
char host[100];
BOOL roopFlag = TRUE;


- (id)initWithSocket:(int) socketDiscri{
	self = [super init];
	if(self != nil)
	{
		clientDescriptorH = socketDiscri;
	}
	
	return self;
}


- (void)runThread
{
	printf("-----Socket run start--------\n");
	memset(clientDataH, 0, sizeof(clientDataH));
	memset(webData, 0, sizeof(webData));
	
	[self clientToWebThread];
	
	[self webToClientThread];
	/*[NSThread detachNewThreadSelector:@selector(webToClientThread) toTarget:self withObject:[NSNumber numberWithInt:1]];
	while(TRUE)
	{
		sleep(30);
		if(roopFlag == FALSE)
		{
			break;
		}
	}*/
	
	printf("-------Socket run close------\n\n");
	close(clientDescriptorH);
	close(webDescriptorH);
}



- (void)webToClientThread
{
	int roopcount = 0;
	NSTimer* timer;
	printf("webToclient Start\n");
	
	
	int a1 = 1;
	while(a1 > 0)
	{
		roopFlag = FALSE;
		/*timer = [NSTimer scheduledTimerWithTimeInterval:1
												 target:self // このselfはretainされる
											   selector:@selector(ontime)
											   userInfo:nil
												repeats:NO];
		[timer invalidate];*/
		
		if(roopcount == 50)
		{
			break;
		}
		printf("webDataReadnow\n");
		
		a1 = read(webDescriptorH, webData, sizeof(webData));
		if(a1 == 0)
		{
			printf("WebSocketが切断された？\n");//a1=1;
			break;
		}
		else if(a1 == -1)
		{
			printf("Error\n");
			break;
		}
		else {
			//break;
		}
		
		printf("webReadByte = %d\n",a1);
		
		
		int a = write(clientDescriptorH, webData, a1);
		printf("write quie\n");
		memset(webData, 0, sizeof(webData));
		if(a == EBADF)
		{
			printf("Descriptor Error\n");
		}
		else if(a == 0)
		{
			printf("write a = 0\n");
		}
		
		roopcount++;
		roopFlag = TRUE;
		
		[timer invalidate];
		[timer release];
	}
	
	printf("webDataReadQuit\n\n");
	[timer invalidate];
	[timer release];
}


- (void)clientToWebThread//¥r¥nで区切りを見分けるべき
{
	int n = 1;
	
	while (n > 0) 
	{//sleep(500);
		printf("\n%d\n",n);
		n = read(clientDescriptorH, clientDataH, sizeof(clientDataH));
		
		//write(webDescriptorH,clientDataH,n);
		printf("quit!!!!\n");
		
		break;/////
	}
	
	printf("recv Message client\n");
	[self changeGetHeader];
	//[self changeConnecitonHeader];////////
	if(webDescriptorH == 0)
	{
		[self setWebDescriptorAndConnect];
	}
	
	printf("n = %d\n",n);
	write(webDescriptorH,clientDataH,n);
	//printf("clientDate%s",clientDataH);
	printf("quit clientToWeb\n");
}

- (void)setWebDescriptorAndConnect
{
	struct hostent *servhost;
	struct sockaddr_in web;
	
	bzero(&web,sizeof(web));
	
	printf("ConnecitonHost = %s\n",host);
	servhost = gethostbyname(host);
	if(servhost == NULL)
	{
		printf("host null\n");
		printf("%s",clientDataH);
	}
	
	web.sin_family = AF_INET;
	bcopy(servhost->h_addr, &web.sin_addr, servhost->h_length);
	web.sin_port = htons(80);
	
	if((webDescriptorH = socket(AF_INET, SOCK_STREAM, 0)) < 0)
	{
		printf("Socket failed\n");
	}
	if(connect(webDescriptorH, (struct sockaddr *)&web, sizeof(web)) == -1)
	{
		printf("WebConnect false\n");
	}
	printf("Connection Web Server\n");
}

-(void)changeConnecitonHeader
{
	char searchWord[] = "Proxy-";
	int size = sizeof searchWord - 1;
	int end = [self byteSearchString:0 searchEnd:2000 searchWord:searchWord wordLength:size];
	//printf("%d start = %c\n",end,clientDataH[end]);
	[self byteShift:end-6 shiftLength:7];
	
	//printf("%s",clientDataH);
}

-(void)changeGetHeader
{
	int k = 0;
	
	printf("changeGetHeader start\n");
	
	int leng1 = [self byteSearch:0 searchEnd:sizeof(clientDataH) searchWord:'G'];
	int leng2 = [self byteSearch:leng1 searchEnd:leng1+3 searchWord:'E'];
	int leng3 = [self byteSearch:leng2 searchEnd:leng2+2 searchWord:'T'];
	int leng4 = [self byteSearch:leng3 searchEnd:2000 searchWord:'/'];
	int leng5 = [self byteSearch:leng4+1 searchEnd:2000 searchWord:'/'];//"//~"
	int leng6 = [self byteSearch:leng5+1 searchEnd:2000 searchWord:'/'];//"~/"
	int leng7 = [self byteSearch:leng3 searchEnd:leng4 searchWord:'h'];
	
	//char urlBuffer[(leng6-1) - (leng5+1) + 1];
	for(int i = leng5+1;i <= leng6-1;i++)
	{
		//urlBuffer[k] = clientDataH[i];
		host[k] = clientDataH[i];
		//	printf("%d = %c\n",i,clientDataH[i]);
		k++;
	}
	
	
	
	[self byteShift:leng7 shiftLength:leng6 - leng7];//GET の　部分を減らす
	
	//printf("%s",clientDataH);	
	
	
}

-(int)byteSearchString:(int)start
			 searchEnd:(int)end
			searchWord:(char[])word
			wordLength:(int)wordLength;
{
	int leng[wordLength];
	int sheek = 0;
	int i = 0;
	
	//printf("searchword %s\n",word);
	
	while(1)//最後までさがすためのループ
	{
		i = 0;
		//int j = [self byteSearch:sheek searchEnd:end searchWord:word[i]];
		//int k = [self byteSearch:sheek searchEnd:end searchWord:word[i+1]];
		//printf("sheek = %d leng[0] = %d leng[1] = %d %c wordLeng = %d\n",sheek,j,k,word[i+1],wordLength);
		
		while(1)//文字列がつながっているかどうかを調べるためのループ
		{
			
			if(i == wordLength)//もしつぎにこえていたら
			{
				return leng[i-1];
				break;
			}
			leng[i] = [self byteSearch:sheek searchEnd:end searchWord:word[i]];
			//printf("leng[%d] = %d ",i,leng[i]);
			if(leng[i] < 0)
			{
				return -1;
			}
			
			if(i+1 == wordLength)//もしつぎに
			{
				return leng[i+1-1];
				break;
			}
			leng[i+1] = [self byteSearch:leng[i]+1 searchEnd:leng[i]+3 searchWord:word[i+1]];
			//printf("leng[%d + 1] = %d ",i + 1,leng[i + 1]);
			if(leng[i + 1] < 0)//つながらなかったら
			{
				sheek = leng[0]+1;
				break;
			}
			sheek = leng[i+1]+1;
			
			//printf("leng[i] = %d sizeof = %d i = %d word[i] = %c word[i+1] = %c\n",leng[i],wordLength,i,word[i],word[i+1]);
			i += 2;
		}
		//printf("sheel = %d\n",sheek);
	}
}

-(void)byteShift:(int)start
	 shiftLength:(int)length;
{
	for(int i = start;i < sizeof(clientDataH) - length;i++)
	{
		clientDataH[i] = clientDataH[i + length];
	}
}

-(int)byteSearch:(int)start
	   searchEnd:(int)end
	  searchWord:(char)word;
{
	for(int i = start;i < end;i++)
	{
		if(clientDataH[i] == word)
		{
			return i;
		}
	}
	
	return -1;
}

-(void)runProxy
{
	[self proxyConnect];
	[self clientToProxy];
	[self webToClientThread];
}

-(void)clientToProxy
{
	[self proxyConnect];
	int n = 0;
	n = read(clientDescriptorH, clientDataH, sizeof(clientDataH));
	printf("%s",clientDataH);
	write(webDescriptorH, clientDataH, n);
}

-(void)proxyConnect
{
	//203.178.133.2 3128 freeServer
	//133.89.159.20 8080 schoolProxy
	char proxyHost[100] = "133.89.159.20";
	int port = 8080;
	struct hostent *servhost;
	struct sockaddr_in web;
	
	servhost = gethostbyname(proxyHost);
	if(servhost == NULL)
	{
		printf("IP Change Falid");
	}
	
	web.sin_family = AF_INET;
	bcopy(servhost->h_addr, &web.sin_addr, servhost->h_length);
	web.sin_port = htons(port);
	
	if((webDescriptorH = socket(AF_INET, SOCK_STREAM, 0)) < 0)
	{
		printf("Socket failed\n");
	}
	if(connect(webDescriptorH, (struct sockaddr *)&web, sizeof(web)) == -1)
	{
		printf("WebConnect false");
	}
	printf("Connection Web Server\n");
}

@end