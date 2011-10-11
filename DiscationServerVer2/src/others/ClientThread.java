package others;
import java.io.*;
import java.net.Socket;
import java.util.Vector;

import obje.JPG;

import Header.*;

public class ClientThread extends Thread{
	private Socket clientSocket;
	private OutputStream clientOutput;
	private InputStream clientInput;
	private DataBase dataBase;
	/**
	 * ServerからClientThreadに割り振られた,Thread識別用ID
	 */
	private int clientNumber;

	public ClientThread(Socket argumentSocket,DataBase database,int number) throws IOException {
		clientSocket = argumentSocket;
		clientOutput = clientSocket.getOutputStream();
		clientInput = clientSocket.getInputStream();
		dataBase = database;
		clientNumber = number;
	}
	
	public void run()
	{
		ClientInterface ci = new ClientInterface(dataBase);

		while(true){
			System.out.println();
			try {
				byte[] headerData;

				headerData = readRequestHeader();
				ci.setMember(headerData);
				//ci.printMember();

				if(Define.objec.SYSTEM == ci.getObjec()){
					if(Define.order.MAJORITYCREATE == ci.getOrder()){
						dataBase.createMajority(ci.getDataOfMajority());
					}
					else if(Define.order.MAJORITYADD == ci.getOrder()){
						dataBase.addMajority(ci.getDataOfMajority());
					}
					else if(Define.order.MAJORITYRESET == ci.getOrder()){
						dataBase.resetMajority();
					}
				}
				else if(Define.objec.NODE == ci.getObjec()){	
					if(Define.order.CREATE == ci.getOrder()){
						Node createNode = ci.getCreateNode();
						dataBase.registrationNode(createNode,clientNumber);
					}
					else if(Define.order.EDIT == ci.getOrder()){
						Node createNode = ci.getCreateNode();
						System.out.println("EditidentNumber = " + createNode.identNumber);///////////////////////////
						dataBase.EditNode(createNode,clientNumber);
					}
					else if(Define.order.DELETE == ci.getOrder()){
						ID delID = ci.getDelId();
						dataBase.DelateNode(delID);
					}
					else
					{
						//未実装
					}
				}
				else if(Define.objec.AGENDA == ci.getObjec()){
					if(Define.order.CREATE == ci.getOrder()){
						AgendaPacket ciAgendaPacket = ci.getAgendaPacket();
						dataBase.getAgendaDataBase().addNode(ciAgendaPacket, ciAgendaPacket.getID());
					}
				}

				Vector cloneDatabase = (Vector)dataBase.getDataBaseClone();
				Vector tempDatabase = (Vector) cloneDatabase.get(0);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				break;
			}
		}
	}

	/**
	 * requestHeader一個分のbyteデータをSocketから読み込みます
	 * @return 読み込んだbyteデータを返します
	 * @throws IOException 
	 */
	public byte[] readRequestHeader() throws IOException
	{
		byte[] headerLengthByte = new byte[4];//Lengthの文を読み込む
		int readLength = clientInput.read(headerLengthByte);
		int headerLength = (headerLengthByte[0] & 0xFF) + (headerLengthByte[1] & 0xFF) * 255 + (headerLengthByte[2] & 0xFF) * 255 * 255 + (headerLengthByte[3] & 0xFF ) * 255 * 255 * 255;

		byte[] headerByte = new byte[headerLength - 4];//HeaderのLengthを抜いた分を読み込む
		while(true){
			int canReadLength = clientInput.available();
			if(canReadLength >= headerByte.length)
			{
				for(int i = 0;i < headerByte.length;){
					int inputRead = clientInput.read(headerByte,i,headerByte.length - i);
					i += inputRead;
					System.out.println("readData = " + inputRead + " headerByteLength = " + headerByte.length + " i =" + i);
				}
				break;//単体テストのモージョル
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		byte[] header = new byte[headerLength];//読み込んだ２つを合成
		for(int i = 0;i < headerLengthByte.length;i++)
		{
			header[i] = headerLengthByte[i];
		}
		for(int i = 0;i < headerByte.length;i++)
		{
			header[headerLengthByte.length + i] = headerByte[i];
		}

		return header;
	}

	public void finalize()
	{
		if(clientSocket != null) {
			try {
				clientSocket.close();
			} catch(IOException e) {

			}
		}
	}
}


