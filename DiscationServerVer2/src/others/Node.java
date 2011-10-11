package others;

import java.util.ArrayList;

import obje.JPG;
import obje.Obje;

import Header.RequestHeader;

/**
 * Clientとオブジェクトをやりとりする際のデータ形式。
 */
public class Node
{
	/**
	 * このNode全体の長さを示す
	 */
	protected int length;
	/**
	 * このノードのdataの種類を示す
	 */
	protected int identNumber;
	/**
	 * このObjectの識別No
	 */
	protected ID id;
	/**
	 * このNodeの本体（本質？主なる部分？）
	 * identNumに対応したObjectが入る(JpgやRoundなど)
	 */
	protected Obje body;

	public Node()
	{
		length = 0;
		identNumber = 0;
		id = new ID();
		body = null;
	}
	
	public void setMember(int argumentIdent,ID argumentId,Obje argumentObje){
		identNumber = argumentIdent;
		System.out.println("NodeSetMember = " + argumentIdent);//////////////////////
		id = argumentId;
		body = argumentObje;
		length = 4 + 1 + id.getLength() + body.getLength();
	}
	
	/**
	 * デバック用のテストデータをメンバにセットするクラスです
	 */
	public void testSeter(Obje obje){
		identNumber = Define.obje.JPG;
		id = new ID();
		id.testSeter();
		body = obje;
		length = 4 + 1 + id.getLength() + ((JPG)obje).getLength();
	}

	public byte[] convertByte() {
		byte[] totalByte = new byte[length];
		totalByte[0] = (byte) (length % 255);
		totalByte[1] = (byte) (length / 255 % 255);
		totalByte[2] = (byte) (length / 255 / 255 % 255);
		totalByte[3] = (byte) (length / 255 / 255 / 255);
		totalByte[4] = (byte) (identNumber);
		
		byte[] idByte = id.convertByte();
		for(int i = 0;i < idByte.length;i++)
		{
			totalByte[5 + i] = idByte[i];
		}
		byte[] bodyByte = body.convertByte();//kokobaguru?
		for(int i = 0;i < bodyByte.length;i++)
		{
			totalByte[5 + idByte.length + i] = bodyByte[i];
		}

		return totalByte;
	}

	/**
	 * byteデータを元に、Memberをセットします
	 * @param nodeData このデータは、Node型に基づいて作られたbyteデータである必要性があります
	 */
	public void setToMember(byte[] nodeData){
		try
		{
			length = (nodeData[0] & 0xFF) + (nodeData[1] & 0xFF)*255 + (nodeData[2] & 0xFF)* 255 * 255 + (nodeData[3]& 0xFF) * 255 * 255 * 255;
			identNumber = (nodeData[4] & 0xFF);
			byte[] idBodyData = new byte[length - 4 - 1];
			for(int i = 0;i < idBodyData.length;i++)
			{
				idBodyData[i] = nodeData[5 + i];
			}
			id.setMember(idBodyData);

			byte[] bodyData = new byte[length - id.convertByte().length - 1 - 4];
			for(int i = 0;i < bodyData.length;i++)
			{
				bodyData[i] = nodeData[5 + id.convertByte().length + i];
			}
			if(identNumber == Define.obje.JPG)
			{
				body = new JPG();
			}
			body.setToMember(bodyData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setToMember()
	{

	}

	public Obje getBody()
	{
		return body;
	}

	public ID getID()
	{
		return id;
	}

	public int getIdentNumber() {
		return identNumber;
	}

	public int getLength()
	{
		return length;
	}
}
