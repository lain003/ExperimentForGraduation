package Header;

import java.io.File;
import java.io.FileInputStream;

import obje.JPG;

import others.*;


public class JPGDataHeaderCreate extends JPGDataHeader{
	protected ID id;
	/**
	 * jpgのData(2)かjpgのNumber(1)が指定されているかを示すフラグ
	 */
	protected int identNumber;
	/**
	 * サーバにあるJPGのナンバーを指定している。このメンバはidentが２だった場合は使用されない
	 */
	protected int jpgNumber;
	/**
	 * jpgのData本体を示す。
	 */
	protected JPG jpg;

	/**
	 * identNumberの値によって動作がかわる。
	 * １の場合はJPGNumberに値がセットされる。２の場合はjpgにデータが格納される
	 * @param requestHeaderData
	 * @param dataBase
	 * @param headerSize このヘッダーのサイズ
	 */
	public void setToMember(byte[] requestHeaderData,int headerSize) {
		id.setMember(requestHeaderData);
		identNumber = (requestHeaderData[id.getDepth()] & 0xFF);
		if(identNumber == 1)
		{
			jpgNumber = (requestHeaderData[id.getDepth() + 1] & 0xFF);
		}
		else if(identNumber == 2)
		{
			headerSize -= id.getDepth();/////ここらバグある可能性あり
			byte[] jpgByte = new byte[headerSize];
			for(int i = 0;i < headerSize;i++)
			{
				jpgByte[i] = requestHeaderData[headerSize + i];
			}
			jpg.setToMember(jpgByte);
		}
	}



	@Override
	public byte[] convertByte() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setToMember(byte[] headerData) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public JPG getJpg()
	{
		return jpg;
	}

	public int getIdnetNumber()
	{
		return identNumber;
	}
	
	public int getJpgNumber()
	{
		return jpgNumber;
	}
	
	public ID getId()
	{
		return id;
	}
}
