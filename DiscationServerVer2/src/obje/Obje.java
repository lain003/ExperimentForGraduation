package obje;

import others.Coordinate;

/**
 * Object�̑��������N���X(Round,Char,JPG�Ȃ�)�͂�����p�����Ȃ��Ă͂Ȃ�Ȃ�
 */
public abstract class Obje {
	public abstract int getLength();
	
	/**
	 * �����o�ϐ����Z�b�g����
	 * @param originData ���̃f�[�^�����ɃZ�b�g����B
	 */
	public abstract void setToMember(byte[] originData);
	
	/**
	 * ���g��byte�ɕϊ������l��Ԃ��܂�
	 * @return �ϊ�����byte�z��
	 */
	public abstract byte[] convertByte();
	
	/**
	 * ���̃I�u�W�F�N�g�������Ă���A�{�W�V�����͈̔͂�����܂�(���Remove����Ƃ��Ɏg�p���܂�)(JPG��0,0��Ԃ��܂�)
	 * @return 
	 */
	public abstract Coordinate[] getScope();
}
