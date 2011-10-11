package obje;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import others.Coordinate;

public class JPG extends Obje{
	private byte[] jpgData;
	
	@Override
	public void setToMember(byte[] data) {
		jpgData = data;
	}

	@Override
	public byte[] convertByte() {
		return jpgData;
	}
	
	/**
	 * MemberのJPGのByteデータを、BufferedImageに変換して返します
	 * @return 変換されたデータ
	 */
	public BufferedImage convertBufferdImage()
	{
		BufferedImage readImage = null;
		
		try {
			readImage = ImageIO.read(new ByteArrayInputStream(jpgData));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return readImage;
	}
	/**
	 * このオブジェの長さを計算して返します。（このオブジェのメンバにlengthはありません）
	 */
	public int getLength()
	{
		int length = 0;
		
		length = jpgData.length;
		
		return length;
	}

	@Override
	public Coordinate[] getScope() {
		Coordinate[] coordinate = new Coordinate[1];
		coordinate[0] = new Coordinate(0,0);
		return coordinate;
	}

}
