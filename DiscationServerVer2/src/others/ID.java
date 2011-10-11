package others;
import java.util.*;

/**
 * ObjectIDを示すクラス
 */
public class ID {
	/**
	 * IDの深さを示す(1byte 255以下にすること)(1-2-1なら深さは２となる　自身は含まない)
	 */
	private int depth = 0;
	/**
	 * IDのある場所を示す
	 */
	private ArrayList<Integer> location = new ArrayList<Integer>();//new?
	/**
	 * Id本体 (未定の場合は２５５が入る)
	 */
	private int id = 255;

	/**
	 * デバック用のテストデータをメンバにセットするクラスです
	 */
	public void testSeter()
	{
		depth = 1;
		location.add(1);
		id = 255;
	}
	
	/**
	 * byteデータから、メンバーをセットします。
	 * @param dataOfHeader 元になるバイトデータです。このバイトデータはID型に基づいて作られている必要性があります
	 */
	public void setMember(byte[] dataOfHeader)
	{
		depth = (dataOfHeader[0] & 0xFF);

		for(int i = 0;i < depth;i++)
		{
			location.add((dataOfHeader[i+1] & 0xFF));
		}
		
		id = (dataOfHeader[1 + depth + 1] & 0xFF);
	}

	/**
	 * このオブジェクトの内容をbyteデータにして返す
	 * @return
	 */
	public byte[] convertByte()
	{
		ArrayList<Byte> totaList = new ArrayList<Byte>();

		totaList.add((byte)depth);
		for(int i=0;i < location.size();i++)
		{
			totaList.add((byte)location.get(i).intValue());
		}
		totaList.add((byte)id);
		
		int listSize = totaList.size();
		byte[] returnByte = new byte[listSize];
		for(int i = 0;i < listSize;i++)
		{
			returnByte[i] = totaList.get(i);
		}
		
		return returnByte;
	}
	
	/**
	 * depthに基づいたこのIDの長さを返します。（このオブジェにlenghtというメンバはありません）
	 * @return
	 */
	public int getLength()
	{
		int length = 0;
		
		length = 1 + depth + 1;
		
		return length;
	}
	
	public int getDepth()
	{
		return depth;
	}
	
	public ArrayList<Integer> getLocation() {
		return location;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int i)
	{
		id = i;
	}
	
	public void setDepth(int i)
	{
		depth = i;
	}
}
