package others;
import java.util.*;

/**
 * ObjectID�������N���X
 */
public class ID {
	/**
	 * ID�̐[��������(1byte 255�ȉ��ɂ��邱��)(1-2-1�Ȃ�[���͂Q�ƂȂ�@���g�͊܂܂Ȃ�)
	 */
	private int depth = 0;
	/**
	 * ID�̂���ꏊ������
	 */
	private ArrayList<Integer> location = new ArrayList<Integer>();//new?
	/**
	 * Id�{�� (����̏ꍇ�͂Q�T�T������)
	 */
	private int id = 255;

	/**
	 * �f�o�b�N�p�̃e�X�g�f�[�^�������o�ɃZ�b�g����N���X�ł�
	 */
	public void testSeter()
	{
		depth = 1;
		location.add(1);
		id = 255;
	}
	
	/**
	 * byte�f�[�^����A�����o�[���Z�b�g���܂��B
	 * @param dataOfHeader ���ɂȂ�o�C�g�f�[�^�ł��B���̃o�C�g�f�[�^��ID�^�Ɋ�Â��č���Ă���K�v��������܂�
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
	 * ���̃I�u�W�F�N�g�̓��e��byte�f�[�^�ɂ��ĕԂ�
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
	 * depth�Ɋ�Â�������ID�̒�����Ԃ��܂��B�i���̃I�u�W�F��lenght�Ƃ��������o�͂���܂���j
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
