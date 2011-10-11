//
//  Socket.m
//  MyProxy
//
//  Created by akitani on 10/08/23.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

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

@implementation Socket

int clientDescriptor;
int çΩ = 0;
BOOL proxy = false;
char clientData[2048];
char webData[2048];
char host[100];

- (id)initWithSocket:(int) socketDiscri{
	self = [super init];
	if(self != nil)
	{
		clientDescriptor = socketDiscri;
	}
	
	return self;
}

- (void)runThread
{
	printf("Socket run start\n");
	memset(clientData, 0, sizeof(clientData));
	memset(webData, 0, sizeof(webData));

	int 
	//[NSThread detachNewThreadSelector:@selector(clientToWebThread) toTarget:self withObject:[NSNumber numberWithInt:1]];
	[NSThread detachNewThreadSelector:@selector(clientToProxy) toTarget:self withObject:[NSNumber numberWithInt:1]];
	[NSThread detachNewThreadSelector:@selector(webToClientThread) toTarget:self withObject:[NSNumber numberWithInt:2]];
}




- (void)webToClientThread
{
	printf("webToclient Start\n");
	while(1)
	{
		if(webDescriptor == 0)
		{
			//printf("webDescriptor 0 \n");
			//sleep(500);
		}
		else 
		{
			int a1 = 1;
			while(a1 > 0)
			{
				printf("webDataReadnow\n\n");
				memset(webData, 0, sizeof(webData));
								//a1 = recv(webDescriptor, webData, sizeof(webData), 0);
				a1 = read(webDescriptor, webData, sizeof(webData));
				if(a1 == 0)
				{
					printf("WebSocketが切断された？\n");
					sleep(500);
				}
				else if(a1 == -1)
				{
					printf("Error\n");
					sleep(500);
				}
				else {
					//break;
				}
				
				printf("webReadByte = %d\n",a1);
				//printf("webData = \n %s",webData);
				for(int i = 0;i < sizeof(webData);i++)
				{
					printf("%c",webData[i]);
					if(i%10 == 0)
					{
						printf("\n");
					}
				}
				
				int a = write(clientDescriptor, webData, a1);
				if(a == EBADF)
				{
					printf("Descriptor Error\n");
				}
				else if(a == 0)
				{
					printf("write a = 0\n");
				}
			}
			
			printf("recv Message Web\n");

			printf("Client send data\n");
		}
	}
}


- (void)clientToWebThread
{
	while(1)
	{
		memset(clientData, 0, sizeof(clientData));
		int a = 1;
		
		int n = 1;
		while (n > 0) 
		{//sleep(500);
			printf("\n%d\n",n);
			memset(clientData, 0, sizeof(clientData));
			n = read(clientDescriptor, clientData, sizeof(clientData));
			if (n < 0) {
				perror("read");
			}
			//write(webDescriptor,clientData,n);
			printf("quit!!!!\n");
			printf("%s",clientData);
			break;/////
		}

		printf("recv Message client\n");
		[self changeGetHeader];
		[self changeConnecitonHeader];
		if(webDescriptor == NULL)
		{
			[self setWebDescriptorAndConnect];
		}
		
		printf("quit clientToWeb\n");
		write(webDescriptor,clientData,n);
		//printf("clientData =\n %s",clientData);
	}
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
		printf("IP Change Falid");
	}

	web.sin_family = AF_INET;
	bcopy(servhost->h_addr, &web.sin_addr, servhost->h_length);
	web.sin_port = htons(80);
	
	if((webDescriptor = socket(AF_INET, SOCK_STREAM, 0)) < 0)
	{
		printf("Socket failed\n");
	}
	if(connect(webDescriptor, (struct sockaddr *)&web, sizeof(web)) == -1)
	{
		printf("WebConnect false");
	}
	printf("Connection Web Server\n");
}

-(void)changeConnecitonHeader
{
	char searchWord[] = "Proxy-";
	int size = sizeof searchWord - 1;
	int end = [self byteSearchString:0 searchEnd:2000 searchWord:searchWord wordLength:size];
	//printf("%d start = %c\n",end,clientData[end]);
	[self byteShift:end-6 shiftLength:7];
	
	//printf("%s",clientData);
}

-(void)changeGetHeader
{
	int k = 0;
	
	printf("changeGetHeader start\n");
	
	int leng1 = [self byteSearch:0 searchEnd:sizeof(clientData) searchWord:'G'];
	int leng2 = [self byteSearch:leng1 searchEnd:leng1+3 searchWord:'E'];
	int leng3 = [self byteSearch:leng2 searchEnd:leng2+2 searchWord:'T'];
	int leng4 = [self byteSearch:leng3 searchEnd:2000 searchWord:'/'];
	int leng5 = [self byteSearch:leng4+1 searchEnd:2000 searchWord:'/'];//"//~"
	int leng6 = [self byteSearch:leng5+1 searchEnd:2000 searchWord:'/'];//"~/"
	int leng7 = [self byteSearch:leng3 searchEnd:leng4 searchWord:'h'];
	
	//char urlBuffer[(leng6-1) - (leng5+1) + 1];
	for(int i = leng5+1;i <= leng6-1;i++)
	{
		//urlBuffer[k] = clientData[i];
		host[k] = clientData[i];
	//	printf("%d = %c\n",i,clientData[i]);
		k++;
	}
	/*for(int i = 0;i < sizeof(urlBuffer);i++)
	{
		printf("%c ",urlBuffer[i]);
	}*/
	
	
	[self byteShift:leng7 shiftLength:leng6 - leng7];//GET の　部分を減らす

	//printf("%s",clientData);	
	
	
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
	for(int i = start;i < sizeof(clientData) - length;i++)
	{
		clientData[i] = clientData[i + length];
	}
}

-(int)byteSearch:(int)start
	   searchEnd:(int)end
	  searchWord:(char)word;
{
	for(int i = start;i < end;i++)
	{
		if(clientData[i] == word)
		{
			return i;
		}
	}
	
	return -1;
}

-(void)clientToProxy
{
	[self proxyConnect];
	int n = 0;
	n = read(clientDescriptor, clientData, sizeof(clientData));
	printf("%s",clientData);
	write(webDescriptor, clientData, n);
}

-(void)proxyConnect
{
	char proxyHost[100] = "192.168.11.116";
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
	
	if((webDescriptor = socket(AF_INET, SOCK_STREAM, 0)) < 0)
	{
		printf("Socket failed\n");
	}
	if(connect(webDescriptor, (struct sockaddr *)&web, sizeof(web)) == -1)
	{
		printf("WebConnect false");
	}
	printf("Connection Web Server\n");
}

@end
