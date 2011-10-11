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
	 * Member��JPG��Byte�f�[�^���ABufferedImage�ɕϊ����ĕԂ��܂�
	 * @return �ϊ����ꂽ�f�[�^
	 */
	public BufferedImage convertBufferdImage()
	{
		BufferedImage readImage = null;
		
		try {
			readImage = ImageIO.read(new ByteArrayInputStream(jpgData));
		} catch (IOException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
		
		return readImage;
	}
	/**
	 * ���̃I�u�W�F�̒������v�Z���ĕԂ��܂��B�i���̃I�u�W�F�̃����o��length�͂���܂���j
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
