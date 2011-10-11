package Header;

import java.net.IDN;
import java.util.ArrayList;

import others.*;
import obje.*;


public class RequestHeader extends Header{
	/**
	 * このヘッダ全体の長さを示す
	 */
	protected int headerLength = 0;
	/**
	 * dataの種類を示す
	 */
	protected int identNumber;
	/**
	 * 命令の種類を表す
	 */
	protected int order;
	/**
	 * 決まっていない領域
	 */
	protected byte[] unDecided = new byte[4];
	/**
	 * data本体
	 */
	protected DataHeader data;

	/**
	 * 引数からHeaderを作成します
	 * @param node 元になるNodeです
	 * @param identnumOrder add.deleate,updateの三種類の命令の中から指定します
	 * @return つくったHeaderのバイトを返します
	 */
	public byte[] createHeader(Node node,int identOrder)
	{
		setToMember(node, identOrder);

		return convertByte();
	}

	/**
	 * Clientから送られてきたデータを解析して、メンバにセットする時に呼ばれる
	 */
	@Override
	public void setToMember(byte[] headerData) {

//		setToMyMember(headerData);
//
//		byte[] dataHeaderByte = new byte[headerLength - 10];
//		for(int i = 0;i < dataHeaderByte.length;i++)
//		{
//			dataHeaderByte[i] = headerData[4 + 1 + 1 + 4 + i];
//		}
//
//		if(identNumber == Define.obje.NODE)
//		{
//			if(order == Define.order.CREATE)
//			{
//				data = new NodeHeaderAdd();
//			}
//		}
//		data.setToMember(dataHeaderByte);
	}

	/**
	 * 引数からメンバをセットします。これは現在の所,Nodeと命令を元にリクエストヘッダを作成するために使われることを想定しています
	 * @param node 元にしたいNodeを指定する
	 * @param orderArgument 命令の種類を表す
	 */
	public void setToMember(Node node,int orderArgument)
	{
//		identNumber = Define.obje.NODE;
//		order = orderArgument;
//		headerLength += 4 + 1 + 1+ 4;
//		if(order == Define.CREATE)
//		{
//			data = new NodeHeaderAdd();
//			((NodeHeaderAdd)data).setToMember(node);
//			headerLength += ((NodeHeaderAdd)data).getLength();
//		}
	}


	/**
	 * ヘッダーデータから自分のメンバーをセットします dataは含まれません
	 * @param headerData
	 */
	protected void setToMyMember(byte[] headerData)
	{
		headerLength = (headerData[0] & 0xFF)+ (headerData[1] & 0xFF)*255+ (headerData[2] & 0xFF)*255*255+ (headerData[3] & 0xFF)* 255 * 255 * 255;
		identNumber = (headerData[4] & 0xFF);
		order = (headerData[5] & 0xFF);
	}

	@Override
	public byte[] convertByte() {
		byte[] headerByte = new byte[headerLength];

		headerByte[0] = (byte) (headerLength % 255);
		headerByte[1] = (byte) (headerLength / 255 % 255);
		headerByte[2] = (byte) (headerLength / 255 / 255 % 255);
		headerByte[3] = (byte) (headerLength / 255 / 255 / 255);
		headerByte[4] = (byte) (identNumber);
		headerByte[5] = (byte) (order);
		byte[] dataByte = data.convertByte();
		for(int i = 0;i < dataByte.length;i++)
		{
			headerByte[10 + i] = dataByte[i];
		}

		return headerByte;
	}

	public int getIdentNumber() {
		return identNumber;
	}

	public int getOrder() {
		return order;
	}

	public int getHeaderLength() {
		return headerLength;
	}

	public DataHeader getData() {
		return data;
	}
}
