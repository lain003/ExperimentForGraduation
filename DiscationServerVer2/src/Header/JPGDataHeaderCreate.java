package Header;

import java.io.File;
import java.io.FileInputStream;

import obje.JPG;

import others.*;


public class JPGDataHeaderCreate extends JPGDataHeader{
	protected ID id;
	/**
	 * jpg��Data(2)��jpg��Number(1)���w�肳��Ă��邩�������t���O
	 */
	protected int identNumber;
	/**
	 * �T�[�o�ɂ���JPG�̃i���o�[���w�肵�Ă���B���̃����o��ident���Q�������ꍇ�͎g�p����Ȃ�
	 */
	protected int jpgNumber;
	/**
	 * jpg��Data�{�̂������B
	 */
	protected JPG jpg;

	/**
	 * identNumber�̒l�ɂ���ē��삪�����B
	 * �P�̏ꍇ��JPGNumber�ɒl���Z�b�g�����B�Q�̏ꍇ��jpg�Ƀf�[�^���i�[�����
	 * @param requestHeaderData
	 * @param dataBase
	 * @param headerSize ���̃w�b�_�[�̃T�C�Y
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
			headerSize -= id.getDepth();/////������o�O����\������
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
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	@Override
	public void setToMember(byte[] headerData) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

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
