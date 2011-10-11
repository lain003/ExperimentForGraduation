package others;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Server{
	private ServerSocket serverSock;
	private Socket clientSocket;
	private DataBase dataBase;

	/**
	 * serverを実行する
	 * @param port Serverのport番号
	 */
	public void myRun(int port)
	{
		try {
			dataBase = new DataBase();
			serverSock = new ServerSocket(port);

			MyJFrame myJframe = new MyJFrame(dataBase);
			myJframe.run();
			AgendaJFrame agendaJFrame = new AgendaJFrame(dataBase);
			agendaJFrame.run();

			//SendScreenMethod sendScreen = new SendScreenMethod(dataBase);
			//sendScreen.start();

			int clientNumberCounter = 0;
			while(true)
			{
				clientNumberCounter++;
				
				clientSocket = serverSock.accept();
				ClientThread clientThread = new ClientThread(clientSocket,dataBase,clientNumberCounter);
				System.out.println("ClientThreadStart");
				clientThread.start();
				dataBase.getMySystem().getConnector().addConnectionList(clientSocket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void finalize()
	{
		if(serverSock != null) {
			try {
				serverSock.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class SendScreenMethod extends Thread{
	private DataBase dataBase;

	public SendScreenMethod(DataBase arrayDataBase){
		dataBase = arrayDataBase;
	}

	public void run(){
		while(true)
		{
			try{
				ArrayList<Socket> socketList = dataBase.getMySystem().getConnector().getConnectSocketListClone();
				if(socketList != null)
				{
					for(int i = 0;i < socketList.size();i++)
					{
						OutputStream output = socketList.get(i).getOutputStream();
						Image screenImage = dataBase.getMySystem().getScreenData();
						byte[] imageByte = getImageBytes((BufferedImage) screenImage, "png");
						Packet packet = new Packet();
						packet.setMember(imageByte);
						output.write(packet.getHeaderByte());
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

	public byte[] getImageBytes(BufferedImage image, String imageFormat)  throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BufferedOutputStream os = new BufferedOutputStream(bos);
		image.flush();
		ImageIO.write(image, imageFormat, os);
		os.flush();
		os.close();
		return bos.toByteArray();
	}
}

class Packet{
	private byte[] headerByte;
	public byte[] getHeaderByte(){
		return headerByte;
	}
	
	public void setMember(byte[] screenImage){
		int imageLength = screenImage.length;
		headerByte = new byte[imageLength + 4]; 
		headerByte[0] = (byte) (imageLength % 255);
		headerByte[1] = (byte) (imageLength / 255 % 255);
		headerByte[2] = (byte) (imageLength / 255 / 255 % 255);
		headerByte[3] = (byte) (imageLength / 255 / 255 / 255);
		for(int i = 0;i < imageLength;i++)
		{
			headerByte[4 + i] = screenImage[i];
		}
	}
}
