package obje;

import java.util.ArrayList;

import others.Coordinate;

/**
 * �|�C���e�B���O�Ɋւ���N���X�ł��B
 */
public class Pointing extends Obje{
	protected ArrayList<Coordinate> coordinatesList = new ArrayList<Coordinate>();
	protected int r;//���a

	public void setR(int R)
	{
		r = R;
	}

	public int getR()
	{
		return r;
	}

	public ArrayList<Coordinate> getCoordinateList()
	{
		return coordinatesList;
	}

	public void setCoordinate(int x,int y)
	{
		Coordinate addCoordinate = new Coordinate();
		addCoordinate.x = x;
		addCoordinate.y = y;

		coordinatesList.add(addCoordinate);
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
		Coordinate[] coordinate = new Coordinate[coordinatesList.size()];
		for(int i = 0;i < coordinate.length;i++)
		{
			coordinate[i] = coordinatesList.get(i);
		}
		
		return coordinate;
	}
}
