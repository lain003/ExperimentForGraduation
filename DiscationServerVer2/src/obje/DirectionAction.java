package obje;

import others.Coordinate;

/**
 * �^�b�`�p�b�h�ŕ��������������߂̃N���X
 */
public class DirectionAction extends Obje{
	
	/**
	 * �^�b�`�p�b�h�ŐG�������p������
	 */
	int direction;
	
	public void setDirection(int direc)
	{
		direction = direc;
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	@Override
	public int getLength() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 0;
	}

	@Override
	public void setToMember(byte[] originData) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public byte[] convertByte() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	@Override
	public Coordinate[] getScope() {
		//������//////////////////////////////
		Coordinate[] returnCoordinate = new Coordinate[0];
		return returnCoordinate;
	}
}
